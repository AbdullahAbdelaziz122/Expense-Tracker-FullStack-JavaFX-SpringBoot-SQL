package com.example.expense_tracker.controllers;

import com.example.expense_tracker.DTO.ApiResponse;
import com.example.expense_tracker.DTO.TransactionRequest;
import com.example.expense_tracker.DTO.TransactionResponse;
import com.example.expense_tracker.exceptions.TransactionCategoryNotFound;
import com.example.expense_tracker.exceptions.UserNotFoundException;
import com.example.expense_tracker.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {


    private final TransactionService transactionService;

    //post
    @PostMapping()
    public ResponseEntity<?> postTransaction(@RequestBody TransactionRequest request){

        try {


            TransactionResponse response = transactionService.createTransaction(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponse<TransactionResponse>(
                            true,
                            "Transaction Created Successfully",
                            response
                    )
            );
        }catch (UserNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, ex.getMessage(), null));
        }catch (TransactionCategoryNotFound ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, ex.getMessage(), null));
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }
}
