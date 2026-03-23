package com.cts.grantserve.controller;

import com.cts.grantserve.dto.UserDto;
import com.cts.grantserve.entity.User;
import com.cts.grantserve.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(user));
    }
    @PostMapping("/login")
    public String UserLoginValidation(@RequestBody User user){
        return userService.UserLoginValidation(user);
    }

}
