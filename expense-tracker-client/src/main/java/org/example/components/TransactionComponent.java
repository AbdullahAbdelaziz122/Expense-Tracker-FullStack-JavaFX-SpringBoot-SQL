package org.example.components;

import javafx.geometry.Pos;
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

        setSpacing(10);
        getStyleClass().addAll("main-background", "rounded-border", "padding-10px");
        setAlignment(Pos.CENTER_LEFT);


        VBox categoryNameDateSection = createCategoryNameDateSection();

        // --- Spacer ---
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);


        // Transaction Amount //
        transactionAmount = new Label("$" + transaction.getAmount());
        transactionAmount.getStyleClass().add("text-size-md");

        if(transaction.getType().equalsIgnoreCase("expense")){
            transactionAmount.getStyleClass().add("text-light-red");
        }else {
            transactionAmount.getStyleClass().add("text-light-green");
        }


        


        this.getChildren().addAll(categoryNameDateSection, spacer, transactionAmount);

    }

    private VBox createCategoryNameDateSection(){
        VBox mainContainer = new VBox();
        transactionName = new Label(transaction.getName());

        // styling
        transactionName.getStyleClass().addAll("text-light-gray", "text-size-md");


        transactionCategory = new Label(transaction.getCategory().getCategoryName());
        transactionCategory.setTextFill(Paint.valueOf("#" + transaction.getCategory().getCategoryColor()));


        transactionDate = new Label(transaction.getDate().toString());
        transactionDate.getStyleClass().addAll("text-light-gray");

        mainContainer.getChildren().addAll(transactionCategory, transactionName, transactionDate);
        return mainContainer;
    }
}
