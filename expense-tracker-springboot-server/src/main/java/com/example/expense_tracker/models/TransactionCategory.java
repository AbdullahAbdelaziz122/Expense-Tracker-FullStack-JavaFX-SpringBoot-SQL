package com.example.expense_tracker.models;

import jakarta.persistence.*;

@Table(name = "transaction_category")
@Entity
public class TransactionCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String CategoryName;

    @Column(nullable = false)
    private String CategoryColor;
}
