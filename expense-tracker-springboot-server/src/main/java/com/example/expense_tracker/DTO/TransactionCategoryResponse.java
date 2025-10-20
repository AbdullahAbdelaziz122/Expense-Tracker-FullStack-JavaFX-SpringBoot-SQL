package com.example.expense_tracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCategoryResponse {
    private Long id;
    private String name;
    private String color;
}
