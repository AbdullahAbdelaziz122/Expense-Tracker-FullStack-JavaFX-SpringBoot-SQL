package com.example.expense_tracker.services;

import com.example.expense_tracker.exceptions.UserNotAuthorizedException;
import com.example.expense_tracker.exceptions.UserNotFoundException;
import com.example.expense_tracker.models.User;
import com.example.expense_tracker.repositories.UserRepository;
import lombok.RequiredArgsConstructor;


import java.util.logging.Logger;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepository;

    public User getUserById(Long userId) {
        logger.info("Getting User by id: " + userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public User getUserByEmail(String email){
        logger.info("Getting User by email: " + email);
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotAuthorizedException::new);
    }


    public User login(String email, String password) {
        logger.info("Attempting login for: " + email);

        User user = getUserByEmail(email);

        if (!password.equals(user.getPassword())) {
            logger.warning("Invalid login attempt for email: " + email);
            throw new UserNotAuthorizedException();
        }

        logger.info("User logged in successfully: " + email);
        return user;
    }
}
