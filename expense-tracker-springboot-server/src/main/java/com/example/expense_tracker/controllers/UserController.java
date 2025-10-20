package com.example.expense_tracker.controllers;


import com.example.expense_tracker.DTO.ApiResponse;
import com.example.expense_tracker.DTO.LoginRequest;
import com.example.expense_tracker.DTO.RegisterRequest;
import com.example.expense_tracker.models.User;
import com.example.expense_tracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserById(@PathVariable Long userId){
        try{
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(new ApiResponse<User>(true, "User Found", user));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @GetMapping()
    public ResponseEntity<?> getUserByEmail(@RequestParam String email){
        try {
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(new ApiResponse<User>(true, "User Found", user));
        }catch (Exception ex){
            return ResponseEntity.ok(new ApiResponse<>(false, ex.getMessage(), null));
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request){

        try {
            User user = userService.login(request.email(), request.password());
            return ResponseEntity.ok(new ApiResponse<User>(true, "Login Successful", user));
        }catch (Exception ex){
            return ResponseEntity.ok(new ApiResponse<>(false, ex.getMessage(), null));
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request){
        try {

            User user = userService.registerUser(request.name(), request.email(), request.password());
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<User>(true, "User Created", user));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }
}
