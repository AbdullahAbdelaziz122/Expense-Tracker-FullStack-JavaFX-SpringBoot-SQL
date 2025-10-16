package com.example.expense_tracker.exceptions;

public class UserNotAuthorizedException extends RuntimeException{
    public UserNotAuthorizedException(){
        super("Email or Password may be incorrect");
    }
}
