package org.example.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.example.components.TransactionComponent;
import org.example.dialogs.CreateOrEditTransactionDialog;
import org.example.models.MonthlyFinance;
import org.example.models.Transaction;
import org.example.models.User;
import org.example.dialogs.CreateNewTransactionCategoryDialog;
import org.example.dialogs.ViewOrEditTransactionCategoryDialog;
import org.example.utils.SqlUtil;
import org.example.views.DashboardView;
import org.example.views.LoginView;

import java.util.List;

public class DashboardController {
    private DashboardView dashboardView;
    private User user;
    // recent transactions section
    private final int recentTransactionSize = 5;
    private int currentPage;
    List<Transaction> transactionsList, currentTransactionsByYear;

    private int currentYear;

    public DashboardController(DashboardView dashboardView, User user){
        this.dashboardView = dashboardView;
        this.currentYear = dashboardView.getYearComboBox().getValue();
        this.user = user;
        initialize();
        fetchUserData();
    }


    // todo: finish this
    private ObservableList<MonthlyFinance> calculateMonthlyFinances(){
        return null;
    }

    private void initialize(){

        addMenuActions();
        addRecentTransactionAction();

    }

    private void fetchUserData(){
        // load the Loading Animations
        dashboardView.getLoadingAnimationPane().setVisible(true);

        // Get recentTransactions
        createRecentTransactionComponents();


        // Get the transactions by the year
        currentTransactionsByYear = SqlUtil.getUserTransactionsByYear(user.getId(), currentYear);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    dashboardView.getLoadingAnimationPane().setVisible(false);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
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

        dashboardView.getLogOutMenuItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new LoginView().show();
            }
        });
    }

    private void createRecentTransactionComponents(){
        transactionsList = SqlUtil.getRecentTransactions(user.getId(), 0, 10);

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




    public void refreshDashboardData(){
        List<Transaction> transactionsList = SqlUtil.getRecentTransactions(user.getId(), 0, 10);

        dashboardView.getRecentTransactionVBox().getChildren().clear();

        if (transactionsList.isEmpty()) return;

        for (Transaction transaction : transactionsList){
            dashboardView.getRecentTransactionVBox().getChildren().add(
                    new TransactionComponent(this, transaction)
            );
        }

        // refresh the Balance Summary box
    }
}
