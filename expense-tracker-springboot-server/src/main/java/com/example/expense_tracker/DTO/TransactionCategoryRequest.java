package com.example.expense_tracker.DTO;

public record TransactionCategoryRequest(Long userId, String categoryName, String categoryColor) {
}
