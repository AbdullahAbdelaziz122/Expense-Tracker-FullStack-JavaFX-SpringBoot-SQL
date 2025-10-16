package com.example.expense_tracker.controllers;


import com.example.expense_tracker.DTO.LoginRequest;
import com.example.expense_tracker.models.User;
import com.example.expense_tracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId){
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }



    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request){
        User user = userService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(Map.of(
                        "message", "login successful",
                        "user", user
        ));
    }
}
