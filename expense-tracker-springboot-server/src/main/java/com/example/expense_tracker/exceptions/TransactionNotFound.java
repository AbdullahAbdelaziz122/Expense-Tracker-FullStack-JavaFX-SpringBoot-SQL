package com.example.expense_tracker.exceptions;


public class TransactionNotFound extends RuntimeException{
    public TransactionNotFound(Long transactionId){
        super("Transaction with Id:{ "+ transactionId+" } does not exist");
    }
}
