package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.example.components.TransactionComponent;
import org.example.dialogs.CreateOrEditTransactionDialog;
import org.example.models.Transaction;
import org.example.models.User;
import org.example.dialogs.CreateNewTransactionCategoryDialog;
import org.example.dialogs.ViewOrEditTransactionCategoryDialog;
import org.example.utils.SqlUtil;
import org.example.views.DashboardView;

import java.util.List;

public class DashboardController {
    private DashboardView dashboardView;
    private User user;

    // recent transactions section
    private final int recentTransactionSize = 5;
    private int currentPage;


    public DashboardController(DashboardView dashboardView, User user){
        this.dashboardView = dashboardView;
        this.user = user;

        initialize();
        createRecentTransactionComponents();
    }

    private void initialize(){

        addMenuActions();
        addRecentTransactionAction();

    }

    private void addRecentTransactionAction() {
        dashboardView.getAddTransactionButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                new CreateOrEditTransactionDialog(DashboardController.this, null,false).showAndWait();
            }
        });
    }

    private void addMenuActions(){
        dashboardView.getCreateNewCategoryMenuItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                new CreateNewTransactionCategoryDialog(DashboardController.this).showAndWait();
            }
        });


        dashboardView.getViewCategoriesMenuItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new ViewOrEditTransactionCategoryDialog(DashboardController.this).showAndWait();
            }
        });
    }

    private void createRecentTransactionComponents(){
        List<Transaction> transactionsList = SqlUtil.getRecentTransactions(user.getId(), 0, 10);

        if (transactionsList.isEmpty()) return;

        for (Transaction transaction : transactionsList){
            dashboardView.getRecentTransactionVBox().getChildren().add(
                    new TransactionComponent(this, transaction)
            );
        }
    }

    public User getUser() {
        return user;
    }
}
