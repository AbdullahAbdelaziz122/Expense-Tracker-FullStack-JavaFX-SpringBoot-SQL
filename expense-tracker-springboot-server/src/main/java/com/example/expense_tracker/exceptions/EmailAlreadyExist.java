package com.example.expense_tracker.exceptions;

public class EmailAlreadyExist extends RuntimeException{
    public EmailAlreadyExist(String email){
        super("Email already exists: " +email);
    }
}
