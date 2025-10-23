package org.example.components;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
    private boolean isEditing;

    public CategoryComponent(DashboardController dashboardController, TransactionCategory transactionCategory) {
        this.dashboardController = dashboardController;
        this.transactionCategory = transactionCategory;

        isEditing = false;

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
        editBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleToggle();
            }
        });

        saveBtn = new Button("Save");
        saveBtn.setMinWidth(50);
        saveBtn.getStyleClass().addAll("text-size-sm");
        saveBtn.setVisible(false);
        saveBtn.setManaged(false);
        saveBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleToggle();
            }
        });


        deleteBtn = new Button("Delete");
        deleteBtn.setMinWidth(50);
        deleteBtn.getStyleClass().addAll("text-size-sm", "bg-light-red", "text-white");


        // --- Add components ---
        getChildren().addAll(categoryTextfield, spacer, colorPicker, editBtn, saveBtn, deleteBtn);
    }

    private void handleToggle() {
        if (!isEditing) {
            isEditing = true;

            // Enable text field
            categoryTextfield.setEditable(true);
            categoryTextfield.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");

            // Enable color picker
            colorPicker.setDisable(false);

            // Hide the edit button
            editBtn.setVisible(false);
            editBtn.setManaged(false);

            // Show the save button
            saveBtn.setVisible(true);
            saveBtn.setManaged(true);
        } else {
            isEditing = false;

            // Disable text field
            categoryTextfield.setEditable(false);
            categoryTextfield.setStyle("-fx-background-color: #515050; -fx-text-fill: #beb989;");

            // Disable color picker
            colorPicker.setDisable(true);

            // Show the edit button
            editBtn.setVisible(true);
            editBtn.setManaged(true);

            // Hide the save button
            saveBtn.setVisible(false);
            saveBtn.setManaged(false);
        }
    }


}
