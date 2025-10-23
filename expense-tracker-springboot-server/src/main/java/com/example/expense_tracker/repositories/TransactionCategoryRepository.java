package com.example.expense_tracker.repositories;

import com.example.expense_tracker.models.TransactionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Long> {
    List<TransactionCategory> getTransactionCategoryByUserId(Long userId);
    TransactionCategory getTransactionCategoryByCategoryName (String categoryName);
}
