package com.example.expense_tracker.controllers;


import com.example.expense_tracker.DTO.ApiResponse;
import com.example.expense_tracker.DTO.LoginRequest;
import com.example.expense_tracker.DTO.RegisterRequest;
import com.example.expense_tracker.models.User;
import com.example.expense_tracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {


    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserById(@PathVariable Long userId){
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(new ApiResponse<User>(true, "User Found", user));
    }

    @GetMapping()
    public ResponseEntity<?> getUserByEmail(@RequestParam String email){
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(new ApiResponse<User>(true, "User Found", user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request){
            User user = userService.login(request.email(), request.password());
            return ResponseEntity.ok(new ApiResponse<User>(true, "Login Successful", user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request){

            User user = userService.registerUser(request.name(), request.email(), request.password());
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<User>(true, "User Created", user));
    }
}
