package com.example.expense_tracker.exceptions;

public class UserCategoryMismatchException extends RuntimeException {
    public UserCategoryMismatchException() {
        super("User does not have access to the specified category");
    }
}
