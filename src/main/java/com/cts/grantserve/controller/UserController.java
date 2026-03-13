package com.cts.grantserve.controller;

import com.cts.grantserve.DTO.UserDto;
import com.cts.grantserve.entity.User;
import com.cts.grantserve.service.IUserService;
import com.cts.grantserve.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(user));
    }

}
