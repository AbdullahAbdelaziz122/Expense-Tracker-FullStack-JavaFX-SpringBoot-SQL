package com.example.expense_tracker.services;

import com.example.expense_tracker.DTO.*;
import com.example.expense_tracker.exceptions.TransactionNotFound;
import com.example.expense_tracker.exceptions.UserCategoryMismatchException;
import com.example.expense_tracker.models.Transaction;
import com.example.expense_tracker.models.TransactionCategory;
import com.example.expense_tracker.models.User;
import com.example.expense_tracker.repositories.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public Transaction getTransactionById(Long id){
        return transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFound(id));
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


    public PaginatedResponse<TransactionResponse> getRecentTransactionsByUser(Long userId, int page, int size) {
        userService.getUserById(userId);

        Page<Transaction> transactionsPage = transactionRepository.findAllByUserIdOrderByDateDesc(
                userId,
                PageRequest.of(page, size)
        );

        // Convert Transaction -> TransactionResponse DTO
        List<TransactionResponse> transactionResponses = transactionsPage.getContent()
                .stream()
                .map(tx -> new TransactionResponse(
                        tx.getId(),
                        tx.getName(),
                        tx.getAmount(),
                        tx.getType(),
                        tx.getDate(),
                        tx.getUser().getId(),
                        tx.getCategory().getId(),
                        tx.getCreatedAt()
                )).toList();
        return new PaginatedResponse<TransactionResponse>(
                transactionResponses,
                transactionsPage.getNumber(),
                transactionsPage.getTotalPages(),
                transactionsPage.getTotalElements(),
                transactionsPage.isLast()
        );

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
        return TransactionToResponseMapper(savedTransaction);
    }

    // update
    public TransactionResponse updateTransaction(TransactionUpdateRequest request){
        // validate transaction
        Transaction oldTransaction = getTransactionById(request.id());

        TransactionCategory category = transactionCategoryService.getTransactionCategoryById(request.categoryId());

        // update transaction
        oldTransaction.setName(request.name());
        oldTransaction.setAmount(request.amount());
        oldTransaction.setType(request.type());
        oldTransaction.setDate(request.date());
        oldTransaction.setCategory(category);

        Transaction newTransaction = transactionRepository.save(oldTransaction);
         return TransactionToResponseMapper(newTransaction);
    }


    // delete
    public void deleteTransaction(Long id){
         // validate transaction
        Transaction transaction = getTransactionById(id);
        transactionRepository.delete(transaction);
    }

    private TransactionResponse TransactionToResponseMapper(Transaction transaction){
        return new TransactionResponse(
                transaction.getId(),
                transaction.getName(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getDate(),
                transaction.getUser().getId(),
                transaction.getCategory().getId(),
                transaction.getCreatedAt()
        );
    }
}
