package com.example.expense_tracker.DTO;

import java.time.LocalDate;

public record TransactionUpdateRequest(Long id, String name, Double amount, String type, LocalDate date, Long categoryId){}
