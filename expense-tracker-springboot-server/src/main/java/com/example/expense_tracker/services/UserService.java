package com.example.expense_tracker.services;

import com.example.expense_tracker.config.BCryptConfig;
import com.example.expense_tracker.exceptions.EmailAlreadyExist;
import com.example.expense_tracker.exceptions.UserNotAuthorizedException;
import com.example.expense_tracker.exceptions.UserNotFoundException;
import com.example.expense_tracker.models.User;
import com.example.expense_tracker.repositories.UserRepository;
import lombok.RequiredArgsConstructor;


import java.time.LocalDate;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepository;
    private final BCryptConfig bCryptConfig;

    public UserService(UserRepository userRepository, BCryptConfig bCryptConfig) {
        this.userRepository = userRepository;
        this.bCryptConfig = bCryptConfig;
    }

    // todo : return user response instead of actual user
    public User getUserById(Long userId) {
        logger.info("Getting User by id: " + userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return  user;
    }

    // todo : return user response instead of actual user
    public User getUserByEmail(String email){
        logger.info("Getting User by email: " + email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    // todo : return user response instead of actual user
    public User login(String email, String password) {
        logger.info("Attempting login for: " + email);
        try {


            User user = getUserByEmail(email);

            if (!bCryptConfig.verifyPassword(password, user.getPassword())) {
                logger.warning("Invalid login attempt for email: " + email);
                throw new UserNotAuthorizedException();
            }

            logger.info("User logged in successfully: " + email);
            return user;
        }catch (UserNotFoundException exception){
            throw new UserNotAuthorizedException();
        }
    }

    // todo : return user response instead of actual user
    public User registerUser(String name, String email, String password) {
        if (checkEmailAlreadyExist(email)) {
            logger.warning("Attempt to register user with existing email: "+ email);
            throw new EmailAlreadyExist(email);
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);

        // hash password
        String hashedPass = bCryptConfig.hash(password);
        user.setPassword(hashedPass);
        user.setCreatedAt(LocalDate.now());

        User savedUser = userRepository.save(user);
        logger.info("New user registered successfully: "+ email);
        return savedUser;
    }


    private boolean checkEmailAlreadyExist(String email){
        return userRepository.findByEmail(email).isPresent();
    }
}
