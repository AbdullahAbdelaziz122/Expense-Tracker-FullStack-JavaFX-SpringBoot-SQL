package com.example.expense_tracker.services;

import com.example.expense_tracker.DTO.TransactionRequest;
import com.example.expense_tracker.DTO.TransactionResponse;
import com.example.expense_tracker.exceptions.UserCategoryMismatchException;
import com.example.expense_tracker.models.Transaction;
import com.example.expense_tracker.models.TransactionCategory;
import com.example.expense_tracker.models.User;
import com.example.expense_tracker.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final TransactionCategoryService transactionCategoryService;
    public TransactionService (TransactionRepository transactionRepository, UserService userService, TransactionCategoryService transactionCategoryService){
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.transactionCategoryService = transactionCategoryService;
    }

    
    // post
    public TransactionResponse createTransaction(TransactionRequest transactionRequest){

        // validate request
        User user = userService.getUserById(transactionRequest.userId());
        TransactionCategory category = transactionCategoryService.getTransactionCategoryById(transactionRequest.categoryId());

        boolean haveCategory =transactionCategoryService.haveCategory(user.getId(), category.getId());

        if(!haveCategory){
            throw new UserCategoryMismatchException();
        }

        Transaction newTransaction = new Transaction();

        newTransaction.setName(transactionRequest.name());
        newTransaction.setAmount(transactionRequest.amount());
        newTransaction.setDate(transactionRequest.date());
        newTransaction.setType(transactionRequest.type());
        newTransaction.setUser(user);
        newTransaction.setCategory(category);
        newTransaction.setCreatedAt(LocalDateTime.now());

        Transaction savedTransaction = transactionRepository.save(newTransaction);
        return new TransactionResponse(
                savedTransaction.getId(),
                savedTransaction.getName(),
                savedTransaction.getAmount(),
                savedTransaction.getType(),
                savedTransaction.getDate(),
                savedTransaction.getUser().getId(),
                savedTransaction.getCategory().getId(),
                savedTransaction.getCreatedAt()
        );
    }
}
