package com.example.expense_tracker.controllers;

import com.example.expense_tracker.DTO.TransactionCategoryRequest;
import com.example.expense_tracker.models.TransactionCategory;
import com.example.expense_tracker.services.TransactionCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.logging.Logger;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/transaction-category")
public class TransactionCategoryController {

    private static final Logger logger = Logger.getLogger(TransactionCategoryController.class.getName());

    private final TransactionCategoryService transactionCategoryService;


    @PostMapping
    public ResponseEntity<?> createTransactionCategory(@RequestBody TransactionCategoryRequest transactionCategoryRequest){
        logger.info("Creating Transaction Category for : "+ transactionCategoryRequest.categoryName());

        TransactionCategory createdTransactionCategory = transactionCategoryService.createTransactionCategory(
                transactionCategoryRequest.userId(),
                transactionCategoryRequest.categoryName(),
                transactionCategoryRequest.categoryColor());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "transaction category created",
                "transaction-category", createdTransactionCategory
        ));

    }
}
