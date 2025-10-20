package com.example.expense_tracker.controllers;

import com.example.expense_tracker.DTO.ApiResponse;
import com.example.expense_tracker.DTO.TransactionCategoryRequest;
import com.example.expense_tracker.DTO.UserTransactionResponse;
import com.example.expense_tracker.models.TransactionCategory;
import com.example.expense_tracker.services.TransactionCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/transaction-category")
public class TransactionCategoryController {

    private static final Logger logger = Logger.getLogger(TransactionCategoryController.class.getName());

    private final TransactionCategoryService transactionCategoryService;

    //get
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllTransactionByUserId(@PathVariable Long userId){
        try {
            UserTransactionResponse userTransactionResponse = transactionCategoryService.getTransactionCategoriesByUserId(userId);

            if(userTransactionResponse.getCategories().isEmpty()){
                return ResponseEntity.ok(new ApiResponse<UserTransactionResponse>(
                        true,
                        "No Transaction categories found for this user.", userTransactionResponse));

            }

            return ResponseEntity.ok(new ApiResponse<UserTransactionResponse>(
                    true,
                    "Transaction categories retrieved successfully.",
                    userTransactionResponse));
        }catch (Exception ex){
            return ResponseEntity.ok(new ApiResponse<>(
                    false,
                    ex.getMessage(),
                    null));
        }
    }

    // post

    @PostMapping
    public ResponseEntity<?> createTransactionCategory(@RequestBody TransactionCategoryRequest transactionCategoryRequest){
        try {
            logger.info("Creating Transaction Category for : "+ transactionCategoryRequest.categoryName());

            TransactionCategory createdTransactionCategory = transactionCategoryService.createTransactionCategory(
                    transactionCategoryRequest.userId(),
                    transactionCategoryRequest.categoryName(),
                    transactionCategoryRequest.categoryColor());

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<TransactionCategory>(
                    true,
                    "Transaction Category created Successfully",
                    createdTransactionCategory
            ));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<TransactionCategory>(
                    false,
                    ex.getMessage(),
                    null
            ));
        }
    }
}
