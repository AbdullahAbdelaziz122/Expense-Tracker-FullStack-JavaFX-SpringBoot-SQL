package com.example.expense_tracker.exceptions;


public class TransactionCategoryAlreadyExist extends RuntimeException{
    public TransactionCategoryAlreadyExist(String categoryName){

        super("Category with name: {"+ categoryName +" } Already exist.");
    }
}
