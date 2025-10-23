package com.example.expense_tracker.DTO;

public record UpdateTransactionCategoryRequest(Long categoryId, String categoryName, String categoryColor) {
}
