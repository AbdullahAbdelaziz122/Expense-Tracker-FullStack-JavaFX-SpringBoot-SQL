package com.example.expense_tracker.controllers;

import com.example.expense_tracker.DTO.*;
import com.example.expense_tracker.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {


    private final TransactionService transactionService;

    //get
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getTransactionsList(@PathVariable Long user_id){
        UserTransactionResponse response = transactionService.getAllTransactionByUserId(user_id);

        if(response.getTransactions().isEmpty()){
            return ResponseEntity.ok().body(new ApiResponse<>(
                    true,
                    "No transactions found for user: "+ user_id,
                    null
            ));
        }
        return ResponseEntity.ok().body(new ApiResponse<>(
                true,
                "Transactions found for user: "+ user_id,
                response
        ));
    }


    @GetMapping("/summary/{user_id}")
    public ResponseEntity<?> getTransactionByUserIdAndYear(
            @PathVariable Long user_id,
            @RequestParam Integer year,
            @RequestParam(required = false) Integer month) {

        UserTransactionResponse response;

        if (month == null) {
            response = transactionService.getTransactionByUserIdAndYear(user_id, year);
        } else {
            response = transactionService.getTransactionsByUserAndYearAndMonth(user_id, year, month);
        }

        if (response.getTransactions().isEmpty()) {
            return ResponseEntity.ok().body(new ApiResponse<>(
                    true,
                    "No transactions found for user: " + user_id,
                    null
            ));
        }

        return ResponseEntity.ok().body(new ApiResponse<>(
                true,
                "Transactions found for user: " + user_id,
                response
        ));
    }


    @GetMapping("/recent/user/{user_id}")
    public ResponseEntity<?> getRecentTransactions(
            @PathVariable Long user_id,
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "5")int size
    ){
        PaginatedResponse<TransactionResponse> response = transactionService.getRecentTransactionsByUser(user_id, page, size);
        return ResponseEntity.ok().body(new ApiResponse<PaginatedResponse<TransactionResponse>>(
                true,
                "Transactions fetched successfully",
                response
        ));
    }
    @GetMapping("/years/{user_id}")
    public ResponseEntity<?> getTransactionsYears(@PathVariable Long user_id){
        List<Integer> years = transactionService.getTransactionYears(user_id);

        if(years.isEmpty()){
            return ResponseEntity.ok().body(new ApiResponse<>(
                    true,
                    "User has no transactions",
                    null
            ));
        }
        return ResponseEntity.ok().body(new ApiResponse<>(
                true,
                "Transactions years found for user",
                years
        ));
    }
    //post
    @PostMapping
    public ResponseEntity<?> postTransaction(@RequestBody TransactionRequest request){

            TransactionResponse response = transactionService.createTransaction(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponse<TransactionResponse>(
                            true,
                            "Transaction Created Successfully",
                            response
                    )
            );
    }

    //put
    @PutMapping
    public ResponseEntity<?> putTransaction(@RequestBody TransactionUpdateRequest request){
        TransactionResponse response = transactionService.updateTransaction(request);
        return ResponseEntity.ok().body(new ApiResponse<TransactionResponse>(
                true,
                "Transaction updated successfully",
                response
        ));
    }

    @DeleteMapping("/{transaction_id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long transaction_id){
        transactionService.deleteTransaction(transaction_id);
        return ResponseEntity.ok().body(new ApiResponse<>(
                true,
                "Transaction deleted successfully",
                null
        ));
    }
}