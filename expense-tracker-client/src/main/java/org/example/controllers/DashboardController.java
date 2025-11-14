package org.example.controllers;

import javafx.collections.FXCollections;
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

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

public class DashboardController {
    private DashboardView dashboardView;
    private User user;
    // recent transactions section
    private final int recentTransactionSize = 5;
    private int currentPage;
    private ObservableList<Transaction> transactionsList = FXCollections.observableArrayList();
    private ObservableList<Transaction> currentTransactionsByYear = FXCollections.observableArrayList();


    private int currentYear;

    public DashboardController(DashboardView dashboardView, User user){
        this.dashboardView = dashboardView;
        this.currentYear = dashboardView.getYearComboBox().getValue();
        this.user = user;
        initialize();
        fetchUserData();
    }


    private ObservableList<MonthlyFinance> calculateMonthlyFinances(){
        double[] incomeCounter = new double[12];
        double[] expenseCounter = new double[12];

        for (Transaction transaction : currentTransactionsByYear){

            if(transaction.getType().equalsIgnoreCase("Income")){
                incomeCounter[transaction.getDate().getMonth().getValue() - 1] += transaction.getAmount();
            }else {
                expenseCounter[transaction.getDate().getMonth().getValue() - 1] += transaction.getAmount();
            }
        }

        ObservableList<MonthlyFinance> monthlyFinances = FXCollections.observableArrayList();
        for (int i = 0; i < 12; i++) {
            MonthlyFinance monthlyFinance = new MonthlyFinance(
                    Month.of(i+ 1).name(),
                    new BigDecimal(String.valueOf(incomeCounter[i])),
                    new BigDecimal(String.valueOf(expenseCounter[i]))
            );

            monthlyFinances.add(monthlyFinance);
        }
        return monthlyFinances;
    }

    private void initialize(){

        addMenuActions();
        addRecentTransactionAction();

    }

    private void fetchUserData(){
        // load the Loading Animations
        dashboardView.getLoadingAnimationPane().setVisible(true);

        // Get the transactions by the year
        currentTransactionsByYear.setAll(SqlUtil.getUserTransactionsByYear(user.getId(), currentYear));

        dashboardView.getTransactionTable().setItems(calculateMonthlyFinances());
        // Get recentTransactions
        createRecentTransactionComponents();

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
        transactionsList.setAll(SqlUtil.getRecentTransactions(user.getId(), 0, 10));

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
        // Refresh in-memory lists
        transactionsList.setAll(SqlUtil.getRecentTransactions(user.getId(), 0, 10));
        currentTransactionsByYear.setAll(SqlUtil.getUserTransactionsByYear(user.getId(), currentYear));

        // Refresh Recent Transactions UI
        dashboardView.getRecentTransactionVBox().getChildren().clear();

        for (Transaction transaction : transactionsList) {
            dashboardView.getRecentTransactionVBox().getChildren().add(
                    new TransactionComponent(this, transaction)
            );
        }

        // Refresh monthly finances table
        dashboardView.getTransactionTable().setItems(calculateMonthlyFinances());
    }

}
