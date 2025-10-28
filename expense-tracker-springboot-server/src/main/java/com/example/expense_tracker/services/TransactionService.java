package com.example.expense_tracker.services;

import com.example.expense_tracker.DTO.TransactionRequest;
import com.example.expense_tracker.DTO.TransactionResponse;
import com.example.expense_tracker.DTO.UserResponse;
import com.example.expense_tracker.DTO.UserTransactionResponse;
import com.example.expense_tracker.exceptions.TransactionNotFound;
import com.example.expense_tracker.exceptions.UserCategoryMismatchException;
import com.example.expense_tracker.models.Transaction;
import com.example.expense_tracker.models.TransactionCategory;
import com.example.expense_tracker.models.User;
import com.example.expense_tracker.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

    // Get
    public TransactionResponse getTransactionById(Long id){
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFound(id));
        return new TransactionResponse(transaction.getId()
                ,transaction.getName()
                ,transaction.getAmount()
                ,transaction.getType()
                ,transaction.getDate()
                ,transaction.getUser().getId()
                ,transaction.getCategory().getId()
                ,transaction.getCreatedAt());
    }

    public UserTransactionResponse getAllTransactionByUserId(Long userId){
        // validate user
        User user = userService.getUserById(userId);
        UserResponse userResponse = new UserResponse(user.getId(), user.getName(), user.getEmail());

        // fetch Transaction by User
        List<Transaction> transactionList = transactionRepository.findAllByUserId(user.getId());
        List<TransactionResponse> transactionResponses = transactionList.stream()
                .map(transaction -> new TransactionResponse(transaction.getId()
                    ,transaction.getName()
                    ,transaction.getAmount()
                    ,transaction.getType()
                    ,transaction.getDate()
                    ,transaction.getUser().getId()
                    ,transaction.getCategory().getId()
                    ,transaction.getCreatedAt())
                ).toList();
        return new UserTransactionResponse(userResponse, transactionResponses);
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
