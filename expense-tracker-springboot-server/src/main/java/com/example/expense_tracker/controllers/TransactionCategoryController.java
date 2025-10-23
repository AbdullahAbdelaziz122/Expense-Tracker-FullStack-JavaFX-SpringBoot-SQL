package com.example.expense_tracker.controllers;

import com.example.expense_tracker.DTO.*;
import com.example.expense_tracker.exceptions.TransactionCategoryAlreadyExist;
import com.example.expense_tracker.exceptions.TransactionCategoryNotFound;
import com.example.expense_tracker.models.TransactionCategory;
import com.example.expense_tracker.services.TransactionCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.ServerProperties;
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
    private final ServerProperties serverProperties;

    //get

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionCategoryById(@PathVariable Long id){
        try {
            TransactionCategory transactionCategory = transactionCategoryService.getTransactionCategoryById(id);
            TransactionCategoryResponse response = new TransactionCategoryResponse(transactionCategory.getId(), transactionCategory.getCategoryName(), transactionCategory.getCategoryColor());

            return ResponseEntity.ok(new ApiResponse<TransactionCategoryResponse>(
                    true,
                    "Transaction category found",
                    response
            ));
        }catch (TransactionCategoryNotFound ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    false,
                    ex.getMessage(),
                    null
            ));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(
                    false,
                    ex.getMessage(),
                    null
            ));
        }
    }

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

            UserTransactionResponse createdTransactionCategory = transactionCategoryService.createTransactionCategory(
                    transactionCategoryRequest.userId(),
                    transactionCategoryRequest.categoryName(),
                    transactionCategoryRequest.categoryColor());

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<UserTransactionResponse>(
                    true,
                    "Transaction Category created Successfully",
                    createdTransactionCategory
            ));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    false,
                    ex.getMessage(),
                    null
            ));
        }
    }

    @PutMapping
    public ResponseEntity<?> updateTransactionCategory(@RequestBody UpdateTransactionCategoryRequest request){
        try {
            TransactionCategoryResponse response = transactionCategoryService.updateTransactionCategoryById(request);

            return ResponseEntity.ok(new ApiResponse<TransactionCategoryResponse>(
                    true,
                    "Transaction category updated successfully",
                    response
            ));
        }catch (TransactionCategoryNotFound ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    false,
                    "Failed to update: " + ex.getMessage(),
                    null
            ));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(
                    false,
                    ex.getMessage(),
                    null
            ));
        }
    }
}
