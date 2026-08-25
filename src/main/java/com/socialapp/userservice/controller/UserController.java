package com.socialapp.userservice.controller;

import com.socialapp.userservice.dto.LoginRequest;
import com.socialapp.userservice.dto.LoginResponse;
import com.socialapp.userservice.dto.UpdateUser;
import com.socialapp.userservice.dto.UserResponse;
import com.socialapp.userservice.model.User;
import com.socialapp.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(user));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String username){
        return ResponseEntity.ok(userService.getUser(username));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }

    @PatchMapping("/{username}/update")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String username, @RequestBody UpdateUser updateUser){
        return ResponseEntity.ok(userService.updateUser(username,updateUser));
    }

}
