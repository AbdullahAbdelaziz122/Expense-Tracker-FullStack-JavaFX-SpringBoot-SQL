package com.example.expense_tracker.DTO;

import java.time.LocalDate;

public record TransactionRequest(String name, Double amount, LocalDate date, String type, Long userId, Long categoryId) {
}
