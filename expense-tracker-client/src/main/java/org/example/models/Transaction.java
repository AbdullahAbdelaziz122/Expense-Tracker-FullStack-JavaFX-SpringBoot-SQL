package org.example.models;

import java.time.LocalDate;

public class Transaction {
    private Long id;
    private String name;
    private Double amount;
    private String type;
    private LocalDate date;
    private TransactionCategory category;



    // constructor
    public Transaction() {
    }

    public Transaction(Long id, String name, Double amount, String type, LocalDate date, TransactionCategory category) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.category = category;
    }

    // getters and setters
    public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
