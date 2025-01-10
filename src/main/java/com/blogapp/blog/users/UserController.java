package com.blogapp.blog.users;

import com.blogapp.blog.users.dtos.CreateUserRequest;
import com.blogapp.blog.users.dtos.CreateUserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UsersService usersService;
    private final ModelMapper modelMapper;

    public UserController(UsersService usersService, ModelMapper modelMapper) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("")
    ResponseEntity<CreateUserResponse> signUpUser(@RequestBody CreateUserRequest request) {
        UserEntity savedUser = usersService.createUser(request);
        URI savedUserUri = URI.create("/users/" + savedUser.getId());
        return ResponseEntity.created(savedUserUri).body(modelMapper.map(savedUser, CreateUserResponse.class));
    }

    @PostMapping("/login")
    void loginUser() {

    }
}
