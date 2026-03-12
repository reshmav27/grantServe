package com.cts.grantserve.controller;

import com.cts.grantserve.entity.User;
import com.cts.grantserve.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public ResponseEntity<String> loginValidation(@RequestParam("userID") Long userID, @RequestParam("password") String Password){
        String result = userService.loginValidation(userID,Password);
        if(result.equals("Login Successful")){
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        else {
           return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(user));
    }


}
