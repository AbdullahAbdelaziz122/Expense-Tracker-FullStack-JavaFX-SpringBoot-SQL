package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.example.components.TransactionComponent;
import org.example.dialogs.*;
import org.example.models.MonthlyFinance;
import org.example.models.Transaction;
import org.example.models.User;
import org.example.utils.SqlUtil;
import org.example.views.DashboardView;
import org.example.views.LoginView;

import java.math.BigDecimal;
import java.math.RoundingMode;
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


    private void initialize(){

        addMenuActions();
        addRecentTransactionAction();
        addComboBoxActions();
        addTableActions();
        addViewChartActions();
    }



    private void fetchUserData(){
        // load the Loading Animations
        dashboardView.getLoadingAnimationPane().setVisible(true);

        // Get the transactions by the year
        currentTransactionsByYear.setAll(SqlUtil.getUserTransactionsByYear(user.getId(), currentYear));

        dashboardView.getTransactionTable().setItems(calculateMonthlyFinances());
        // Get recentTransactions
        createRecentTransactionComponents();

        calculateBalanceIncomeExpense();

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

    private void calculateBalanceIncomeExpense(){
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Transaction transaction : currentTransactionsByYear) {

            if (transaction.getType().equalsIgnoreCase("Income")) {
                totalIncome = totalIncome.add(BigDecimal.valueOf(transaction.getAmount()));
            } else {
                totalExpense = totalExpense.add(BigDecimal.valueOf(transaction.getAmount()));
            }
        }

        totalIncome = totalIncome.setScale(2, RoundingMode.HALF_UP);
        totalExpense = totalExpense.setScale(2, RoundingMode.HALF_UP);

        BigDecimal currentBalance = totalIncome.subtract(totalExpense)
                .setScale(2, RoundingMode.HALF_UP);

        dashboardView.getTotalIncome().setText("$" + totalIncome);
        dashboardView.getTotalExpense().setText("$" + totalExpense);
        dashboardView.getCurrentBalance().setText("$" + currentBalance);
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

    private void createRecentTransactionComponents(){
        transactionsList.setAll(SqlUtil.getRecentTransactions(user.getId(), 0, 10));

        if (transactionsList.isEmpty()) return;

        for (Transaction transaction : transactionsList){
            dashboardView.getRecentTransactionVBox().getChildren().add(
                    new TransactionComponent(this, transaction)
            );
        }
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

    private void addTableActions(){
        dashboardView.getTransactionTable().setRowFactory(new Callback<TableView<MonthlyFinance>, TableRow<MonthlyFinance>>() {
            @Override
            public TableRow<MonthlyFinance> call(TableView<MonthlyFinance> monthlyFinanceTableView) {
                TableRow<MonthlyFinance> row = new TableRow<>();
                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        MonthlyFinance monthlyFinance = row.getItem();
                        if(!row.isEmpty() && mouseEvent.getClickCount() == 2){
                            new ViewTransactionDialog(DashboardController.this, monthlyFinance.getMonth())
                                    .showAndWait();
                        }
                    }
                });
                return row;
            }
        });
    }

    private void addViewChartActions(){
        dashboardView.getViewChartButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                new ViewChartDialog(DashboardController.this, dashboardView.getTransactionTable().getItems()).showAndWait();
            }
        });
    }


    public void addComboBoxActions(){
        dashboardView.getYearComboBox().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                currentYear = dashboardView.getYearComboBox().getValue();

                // refresh
                refreshDashboardData();
            }
        });
    }


    public void refreshDashboardData(){

        dashboardView.getLoadingAnimationPane().setVisible(true);

        // Refresh in-memory lists
        transactionsList.setAll(SqlUtil.getRecentTransactions(user.getId(), 0, 10));
        dashboardView.getYearComboBox().getItems().setAll(SqlUtil.getTransactionYears(user.getId()));
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

        calculateBalanceIncomeExpense();
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

    public User getUser() {
        return user;
    }

    public int getCurrentYear() {
        return currentYear;
    }
}
