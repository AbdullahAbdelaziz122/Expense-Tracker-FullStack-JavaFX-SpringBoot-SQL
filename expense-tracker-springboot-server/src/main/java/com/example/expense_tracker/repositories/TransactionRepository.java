package com.example.expense_tracker.repositories;

import com.example.expense_tracker.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByUserId(Long userId);

    Page<Transaction> findAllByUserIdOrderByDateDesc(Long userId, Pageable pageable);

    List<Transaction> findAllByUserIdAndDateBetweenOrderByDateDesc(
            Long userId, LocalDate startDate, LocalDate endDate
    );



    @Query("SELECT DISTINCT YEAR(t.date) FROM Transaction t WHERE t.user.id = :userId ORDER BY YEAR(t.date) DESC")
    List<Integer> findDistinctYearsByUserId(Long userId);
}

