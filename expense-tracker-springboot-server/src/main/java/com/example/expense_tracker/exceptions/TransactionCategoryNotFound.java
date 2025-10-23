package com.example.expense_tracker.exceptions;


public class TransactionCategoryNotFound extends RuntimeException{
    public TransactionCategoryNotFound(Long categoryId){
        super("Category with Id:{ "+ categoryId+" } does not exist");
    }
}
