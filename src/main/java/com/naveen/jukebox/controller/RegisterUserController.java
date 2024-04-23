package com.naveen.jukebox.controller;

import com.naveen.jukebox.model.UserRequest;
import com.naveen.jukebox.model.UserResponse;
import com.naveen.jukebox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegisterUserController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/user")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest request) {
        UserResponse response = userService.createUser(request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(201));
    }
}
