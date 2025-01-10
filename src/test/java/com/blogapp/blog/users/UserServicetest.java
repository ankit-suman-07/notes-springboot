package com.blogapp.blog.users;

import com.blogapp.blog.users.dtos.CreateUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserServicetest {

    @Autowired
    UsersService usersService;

    @Test
    void can_create_users() {
        var user = usersService.createUser(new CreateUserRequest("john", "pass123", "john@doe.com"));

        Assertions.assertNotNull(user);
        Assertions.assertEquals("john", user.getUsername());
    }
}
