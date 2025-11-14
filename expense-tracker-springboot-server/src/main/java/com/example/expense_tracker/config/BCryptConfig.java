package com.example.expense_tracker.config;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BCryptConfig {
    private static final Logger log = LoggerFactory.getLogger(BCryptConfig.class);

    private final int logRounds;

    public BCryptConfig(int logRounds) {
        this.logRounds = logRounds;
    }

    public String hash(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(logRounds));
    }

    public boolean verifyPassword(String password, String hashedPassword){
        return BCrypt.checkpw(password, hashedPassword);
    }


}
