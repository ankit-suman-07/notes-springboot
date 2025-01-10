package com.blogapp.blog.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(String username, String password, String email) {
        var newUser = UserEntity.builder()
                .username(username)
                .email(email)
                .build();

        return userRepository.save(newUser);
    }

    public UserEntity getUser(String username) {
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

    class UserNotFoundException extends IllegalArgumentException {
        public UserNotFoundException(String username) {
            super("User " + username + " not found!!!");
        }
    }
}
