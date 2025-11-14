package org.example.dialogs;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.example.components.TransactionComponent;
import org.example.controllers.DashboardController;
import org.example.models.Transaction;
import org.example.utils.SqlUtil;

import java.time.Month;
import java.util.List;

public class ViewTransactionDialog extends CustomDialog{

    private DashboardController dashboardController;
    private String month;

    public ViewTransactionDialog(DashboardController dashboardController, String month) {
        super(dashboardController);
        this.dashboardController =dashboardController;
        this.month = month;


        setTitle("View Transactions");
        setWidth(815);
        setHeight(500);

        ScrollPane transactionsScrollPane = createTransactionsScrollPane();
        getDialogPane().setContent(transactionsScrollPane);
    }

    private ScrollPane createTransactionsScrollPane(){

        // Inner VBox that will actually hold the transaction items
        VBox vBox = new VBox(20);
        vBox.setMinHeight(getHeight() - 40);
        vBox.setFillWidth(true);

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setMinHeight(getHeight() - 40);
        scrollPane.setFitToWidth(true);


        List<Transaction> monthTransactions = SqlUtil
                .getUserTransactionsByYear(dashboardController.getUser().getId(), dashboardController.getCurrentYear(), Month.valueOf(month).getValue());

        for (Transaction transaction : monthTransactions){
            TransactionComponent transactionComponent = new TransactionComponent(
                    dashboardController,
                    transaction
            );

            transactionComponent.getStyleClass().addAll("border-light-gray");

            vBox.getChildren().add(transactionComponent);
        }
        return  scrollPane;
    }
}
