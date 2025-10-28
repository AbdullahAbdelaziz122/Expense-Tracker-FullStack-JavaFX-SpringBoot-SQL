package com.example.expense_tracker.DTO;


import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionResponse {

    private Long id;
    private String name;
    private double amount;
    private String type;
    private LocalDate createdAt;

    private Long userId;
    private Long categoryId;
}
