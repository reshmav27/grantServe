package com.cts.grantserve.controller;

import com.cts.grantserve.dto.UserDto;
import com.cts.grantserve.entity.User;
import com.cts.grantserve.projection.IUserProjection;
import com.cts.grantserve.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/fetchUser/{userId}")
    public ResponseEntity<IUserProjection> fetchUser(@PathVariable Long userId){
        return ResponseEntity.status(200).body(userService.fetchUser(userId));
    }
    @GetMapping("/csrf_token")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long userId){
        return ResponseEntity.ok(userService.updateUser(userId,userDto));
    }


}
