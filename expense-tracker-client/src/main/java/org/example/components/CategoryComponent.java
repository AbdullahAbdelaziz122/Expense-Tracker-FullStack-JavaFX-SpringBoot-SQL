package org.example.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import org.example.controllers.DashboardController;
import org.example.models.TransactionCategory;

public class CategoryComponent extends HBox {

    private DashboardController dashboardController;
    private TransactionCategory transactionCategory;

    private TextField categoryTextfield;
    private ColorPicker colorPicker;
    private Button editBtn, saveBtn, deleteBtn;

    public CategoryComponent(DashboardController dashboardController, TransactionCategory transactionCategory) {
        this.dashboardController = dashboardController;
        this.transactionCategory = transactionCategory;

        setSpacing(10);
        setAlignment(Pos.CENTER_LEFT);
        getStyleClass().addAll("rounded-border", "field-background", "padding-10px");

        // --- TextField ---
        categoryTextfield = new TextField(transactionCategory.getCategoryName());
        categoryTextfield.setMaxWidth(Double.MAX_VALUE);
        categoryTextfield.setEditable(false);
        categoryTextfield.getStyleClass().addAll("field-background", "text-size-md", "text-light-gray");
        categoryTextfield.setAlignment(Pos.CENTER_LEFT);

        // --- Spacer ---
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // --- ColorPicker ---
        colorPicker = new ColorPicker(Color.valueOf(transactionCategory.getCategoryColor()));
        colorPicker.setDisable(true);
        colorPicker.getStyleClass().addAll("text-size-sm");

        // --- Buttons ---
        editBtn = new Button("Edit");
        editBtn.setMinWidth(50);
        editBtn.getStyleClass().addAll("text-size-sm");

        saveBtn = new Button("Save");
        saveBtn.setMinWidth(50);
        saveBtn.getStyleClass().addAll("text-size-sm");

        deleteBtn = new Button("Delete");
        deleteBtn.setMinWidth(50);
        deleteBtn.getStyleClass().addAll("text-size-sm", "bg-light-red", "text-white");

        // --- Add components ---
        getChildren().addAll(categoryTextfield, spacer, colorPicker, editBtn, saveBtn, deleteBtn);
    }
}
