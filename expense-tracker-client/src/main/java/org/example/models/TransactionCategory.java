package org.example.models;

public class TransactionCategory {
    private Long id;
    private String categoryName;
    private String categoryColor;

    public TransactionCategory() {
    }

    public TransactionCategory(Long id, String categoryName, String categoryColor) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(String categoryColor) {
        this.categoryColor = categoryColor;
    }
}
