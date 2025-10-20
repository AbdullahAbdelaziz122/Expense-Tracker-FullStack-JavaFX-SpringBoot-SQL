package com.example.expense_tracker.exceptions;


public class TransactionCategoryAlreadyExist extends RuntimeException{
    public TransactionCategoryAlreadyExist(String categoryName){

        super(String.format("Category with name: {} Already exist.", categoryName));
    }
}
