package com.blogapp.blog.users;

import com.blogapp.blog.users.dtos.CreateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser(CreateUserRequest request) {
        UserEntity newUser = modelMapper.map(request, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
//        var newUser = UserEntity.builder()
//                .username(request.getUsername())
//                //.password(request.getPassword())
//                .email(request.getEmail())
//                .build();

        return userRepository.save(newUser);
    }

    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserEntity loginUser(String username, String password) {
        var user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UserNotFoundException(username);
        }

        var passMatch = passwordEncoder.matches(password, user.getPassword());
        if(!passMatch) {
            throw  new InvalidCredentialException();
        }
        return user;
    }

    public static class UserNotFoundException extends IllegalArgumentException {
        public UserNotFoundException(String username) {
            super("User " + username + " not found!!!");
        }

        public UserNotFoundException(Long userId) {
            super("User with id: " + userId + " not found!!!");
        }
    }

    public static class InvalidCredentialException extends IllegalArgumentException {
        public InvalidCredentialException() {
            super("Invalid username or password combination!!!");
        }
    }
}
