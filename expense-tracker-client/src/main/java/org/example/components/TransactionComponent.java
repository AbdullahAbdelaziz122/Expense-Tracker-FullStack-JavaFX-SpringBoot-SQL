package org.example.components;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import org.example.controllers.DashboardController;
import org.example.models.Transaction;
import org.example.utils.SqlUtil;

public class TransactionComponent extends HBox {
    private Label transactionName, transactionCategory, transactionDate, transactionAmount;
    private Button editButton, delButton;

    private final DashboardController dashboardController;
    private final Transaction transaction;

    public TransactionComponent(DashboardController dashboardController, Transaction transaction) {
        this.dashboardController = dashboardController;
        this.transaction = transaction;

        setSpacing(10);
        setAlignment(Pos.CENTER_LEFT);
        getStyleClass().addAll("main-background", "rounded-border", "padding-10px");

        VBox categoryNameDateSection = createCategoryNameDateSection();

        // Spacer between left info and right controls
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Transaction amount label
        transactionAmount = new Label("$" + transaction.getAmount());
        transactionAmount.getStyleClass().add("text-size-md");

        if (transaction.getType().equalsIgnoreCase("expense")) {
            transactionAmount.getStyleClass().add("text-light-red");
        } else {
            transactionAmount.getStyleClass().add("text-light-green");
        }

        // Action buttons (edit + delete)
        HBox actionButtons = createActionButtons();

        // Add all parts to layout
        this.getChildren().addAll(categoryNameDateSection, spacer, transactionAmount, actionButtons);
    }

    private VBox createCategoryNameDateSection() {
        VBox mainContainer = new VBox();

        transactionName = new Label(transaction.getName());
        transactionName.getStyleClass().addAll("text-light-gray", "text-size-md");

        transactionCategory = new Label(transaction.getCategory().getCategoryName());
        transactionCategory.setTextFill(Paint.valueOf("#" + transaction.getCategory().getCategoryColor()));

        transactionDate = new Label(transaction.getDate().toString());
        transactionDate.getStyleClass().addAll("text-light-gray");

        mainContainer.getChildren().addAll(transactionCategory, transactionName, transactionDate);
        return mainContainer;
    }

    private HBox createActionButtons() {
        HBox buttonsContainer = new HBox(10);
        buttonsContainer.setAlignment(Pos.CENTER);

        editButton = new Button("Edit");
        editButton.getStyleClass().addAll("text-size-md", "rounded-border");

        delButton = new Button("Del");
        delButton.getStyleClass().addAll("text-size-md", "rounded-border", "text-white", "bg-light-red");

        // Delete action with confirmation
        delButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert alert = new Alert(
                        Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to delete this transaction?",
                        ButtonType.YES, ButtonType.NO
                );
                alert.setHeaderText(null);

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        SqlUtil.deleteTransaction(transaction.getId());

                        // Remove from parent VBox after deletion
                        if (getParent() instanceof VBox parentVBox) {
                            parentVBox.getChildren().remove(TransactionComponent.this);
                        }

                        // todo: Optionally, refresh dashboard summary
                        //dashboardController.refreshDashboardData();
                    }
                });
            }
        });

        buttonsContainer.getChildren().addAll(editButton, delButton);
        return buttonsContainer;
    }
}
