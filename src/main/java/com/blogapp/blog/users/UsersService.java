package com.blogapp.blog.users;

import com.blogapp.blog.users.dtos.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(CreateUserRequest request) {
        var newUser = UserEntity.builder()
                .username(request.getUsername())
                //.password(request.getPassword()) // TODO: Encrypt password
                .email(request.getEmail())
                .build();

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

        // TODO: match password
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
}
