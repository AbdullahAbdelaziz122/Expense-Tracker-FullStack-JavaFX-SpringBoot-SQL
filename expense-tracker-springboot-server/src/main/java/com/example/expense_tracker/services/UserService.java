package com.example.expense_tracker.services;

import com.example.expense_tracker.exceptions.EmailAlreadyExist;
import com.example.expense_tracker.exceptions.UserNotAuthorizedException;
import com.example.expense_tracker.exceptions.UserNotFoundException;
import com.example.expense_tracker.models.User;
import com.example.expense_tracker.repositories.UserRepository;
import lombok.RequiredArgsConstructor;


import java.time.LocalDate;
import java.time.LocalDateTime;
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
                .orElseThrow(() -> new UserNotFoundException(email));
    }


    public User login(String email, String password) {
        logger.info("Attempting login for: " + email);
        try {


            User user = getUserByEmail(email);

            if (!password.equals(user.getPassword())) {
                logger.warning("Invalid login attempt for email: " + email);
                throw new UserNotAuthorizedException();
            }

            logger.info("User logged in successfully: " + email);
            return user;
        }catch (UserNotFoundException exception){
            throw new UserNotAuthorizedException();
        }
    }


    public User registerUser(String name, String email, String password) {
        if (checkEmailAlreadyExist(email)) {
            logger.warning("Attempt to register user with existing email: "+ email);
            throw new EmailAlreadyExist(email);
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        //todo : Hash the password before saving it to the database
        user.setPassword(password);
        user.setCreatedAt(LocalDate.now());

        User savedUser = userRepository.save(user);
        logger.info("New user registered successfully: "+ email);
        return savedUser;
    }


    private boolean checkEmailAlreadyExist(String email){
        return userRepository.findByEmail(email).isPresent();
    }
}
