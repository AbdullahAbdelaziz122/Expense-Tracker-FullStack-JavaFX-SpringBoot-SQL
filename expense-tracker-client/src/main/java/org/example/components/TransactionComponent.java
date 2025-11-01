package org.example.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.example.controllers.DashboardController;
import org.example.models.Transaction;
import org.example.utils.Utility;

public class TransactionComponent extends HBox {
    private Label transactionName, transactionCategory, transactionDate, transactionAmount;
    private Button editButton, delButton;

    private DashboardController dashboardController;
    private Transaction transaction;

    public TransactionComponent(DashboardController dashboardController, Transaction transaction){
        this.dashboardController = dashboardController;
        this.transaction = transaction;

        VBox categoryNameDateSection = createCategoryNameDateSection();

        // --- Spacer ---
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);


        // Transaction Amount //
        transactionAmount = new Label("$" + transaction.getAmount());
        transactionAmount.getStyleClass().add("text-size-md");



        this.getChildren().addAll(categoryNameDateSection, spacer);

    }

    private VBox createCategoryNameDateSection(){
        VBox mainContainer = new VBox();
        mainContainer.getStyleClass().addAll("");

        transactionName = new Label(transaction.getName());
        transactionName.getStyleClass().addAll("text-light-gray", "text-size-md");


        transactionCategory = new Label(transaction.getCategory().getCategoryName());
        transactionCategory.setTextFill(Paint.valueOf("#" + transaction.getCategory().getCategoryColor()));


        transactionDate = new Label(transaction.getDate().toString());
        transactionDate.getStyleClass().addAll("text-light-gray");

        mainContainer.getChildren().addAll(transactionCategory, transactionName, transactionDate);
        return mainContainer;
    }
}
