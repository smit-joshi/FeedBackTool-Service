package com.ga.domain.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ga.repository.UserService;

@RestController
@RequestMapping("/user/")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/auth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String userLogin(@RequestParam("email") String email, @RequestParam("password") String password) {
        LOGGER.info("User login called!!");

        LOGGER.info("User login complete!!");
        return password;
    }
}