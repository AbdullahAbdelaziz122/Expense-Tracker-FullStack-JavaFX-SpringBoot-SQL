package com.example.expense_tracker.repositories;

import com.example.expense_tracker.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByUserId(Long userId);
    Page<Transaction> findAllByUserIdOrderByDateDesc(Long userId, Pageable pageable);
    List<Transaction> findAllByUserIdAndDateBetweenOrderByDateDesc(
            Long userId, LocalDate startDate, LocalDate endDate
    );

}
