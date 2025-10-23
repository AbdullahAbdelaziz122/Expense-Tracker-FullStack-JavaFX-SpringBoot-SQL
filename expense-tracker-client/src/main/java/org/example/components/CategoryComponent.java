package org.example.components;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.example.controllers.DashboardController;
import org.example.models.TransactionCategory;

public class CategoryComponent  extends HBox {

    private DashboardController dashboardController;
    private TransactionCategory transactionCategory;

    private TextField categoryTextfield;
    private ColorPicker colorPicker;
    private Button editBtn, saveBtn, deleteBtn;


    public CategoryComponent(DashboardController dashboardController, TransactionCategory transactionCategory){
        this.dashboardController = dashboardController;
        this.transactionCategory = transactionCategory;


        categoryTextfield = new TextField();
        categoryTextfield.setText(transactionCategory.getCategoryName());
        categoryTextfield.setMaxWidth(Double.MAX_VALUE);
        categoryTextfield.setEditable(false);


        colorPicker = new ColorPicker();
        colorPicker.setValue(Color.valueOf(transactionCategory.getCategoryColor()));
        colorPicker.setMaxWidth(Double.MAX_VALUE);
        colorPicker.setEditable(false);


        editBtn = new Button("Edit");
        editBtn.setMinWidth(50);


        saveBtn = new Button("Save");
        saveBtn.setMinWidth(50);


        deleteBtn = new Button("Delete");
        deleteBtn.setMinWidth(50);


        getChildren().addAll(categoryTextfield, colorPicker, editBtn, saveBtn, deleteBtn);

    }
}
