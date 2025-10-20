package com.example.expense_tracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTransactionResponse {
    private UserResponse user;
    private List<TransactionCategoryResponse> categories;
}
