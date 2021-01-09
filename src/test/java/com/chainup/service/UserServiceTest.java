package com.chainup.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author lili
 * @date 2020/10/23 2:05
 * @see
 * @since
 */
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void createUser() {
        userService.createUser("", "111", "");
    }

    @Test
    void isUserExist() {
    }
}