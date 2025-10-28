package com.example.expense_tracker.DTO;

public record TransactionRequest(String name, Double amount, String type, Long userId, Long categoryId) {
}
