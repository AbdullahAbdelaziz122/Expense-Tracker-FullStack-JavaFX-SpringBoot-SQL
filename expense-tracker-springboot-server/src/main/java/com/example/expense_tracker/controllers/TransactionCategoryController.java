package com.example.expense_tracker.controllers;

import com.example.expense_tracker.DTO.*;
import com.example.expense_tracker.models.TransactionCategory;
import com.example.expense_tracker.services.TransactionCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

            TransactionCategory transactionCategory = transactionCategoryService.getTransactionCategoryById(id);
            TransactionCategoryResponse response = new TransactionCategoryResponse(transactionCategory.getId(), transactionCategory.getCategoryName(), transactionCategory.getCategoryColor());

            return ResponseEntity.ok(new ApiResponse<TransactionCategoryResponse>(
                    true,
                    "Transaction category found",
                    response
            ));

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllTransactionByUserId(@PathVariable Long userId){

            UserTransactionCategoryResponse userTransactionCategoryResponse = transactionCategoryService.getTransactionCategoriesByUserId(userId);

            if(userTransactionCategoryResponse.getCategories().isEmpty()){
                return ResponseEntity.ok(new ApiResponse<UserTransactionCategoryResponse>(
                        true,
                        "No Transaction categories found for this user.", userTransactionCategoryResponse));

            }

            return ResponseEntity.ok(new ApiResponse<UserTransactionCategoryResponse>(
                    true,
                    "Transaction categories retrieved successfully.",
                    userTransactionCategoryResponse));

    }

    // post

    @PostMapping
    public ResponseEntity<?> createTransactionCategory(@RequestBody TransactionCategoryRequest transactionCategoryRequest){

            logger.info("Creating Transaction Category for : "+ transactionCategoryRequest.categoryName());

            UserTransactionCategoryResponse createdTransactionCategory = transactionCategoryService.createTransactionCategory(
                    transactionCategoryRequest.userId(),
                    transactionCategoryRequest.categoryName(),
                    transactionCategoryRequest.categoryColor());

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<UserTransactionCategoryResponse>(
                    true,
                    "Transaction Category created Successfully",
                    createdTransactionCategory
            ));

    }

    // update

    @PutMapping
    public ResponseEntity<?> updateTransactionCategory(@RequestBody UpdateTransactionCategoryRequest request){

            TransactionCategoryResponse response = transactionCategoryService.updateTransactionCategoryById(request);

            return ResponseEntity.ok(new ApiResponse<TransactionCategoryResponse>(
                    true,
                    "Transaction category updated successfully",
                    response
            ));

    }

    // delete

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransactionCategory(@PathVariable Long id){

            transactionCategoryService.deleteTransactionCategoryById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse<>(
                            true,
                            "Category deleted successfully",
                            null
                    )
            );

        }
}
