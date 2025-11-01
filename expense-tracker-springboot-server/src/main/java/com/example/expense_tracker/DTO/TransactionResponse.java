package com.example.expense_tracker.DTO;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionResponse {

    private Long id;
    private String name;
    private double amount;
    private String type;
    private LocalDate date;
    private Long userId;
    private TransactionCategoryResponse category;
    private LocalDateTime createdAt;
}
