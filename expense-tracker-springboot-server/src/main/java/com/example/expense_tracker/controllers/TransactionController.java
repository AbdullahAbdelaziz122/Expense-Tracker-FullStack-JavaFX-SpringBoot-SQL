package com.example.expense_tracker.controllers;

import com.example.expense_tracker.DTO.*;
import com.example.expense_tracker.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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