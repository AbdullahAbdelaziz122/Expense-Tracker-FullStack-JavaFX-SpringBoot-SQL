package org.example.dialogs;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.example.controllers.DashboardController;

public class ViewTransactionDialog extends CustomDialog{

    private DashboardController dashboardController;
    private String month;

    public ViewTransactionDialog(DashboardController dashboardController, String month) {
        super(dashboardController);
        this.dashboardController =dashboardController;
        this.month = month;


        setTitle("View Transactions");

        ScrollPane transactionsScrollPane = createTransactionsScrollPane();
    }

    private ScrollPane createTransactionsScrollPane(){

        // Inner VBox that will actually hold the transaction items
        VBox transactionVBox = new VBox(10);
        transactionVBox.setSpacing(8);

        // Scroll pane wrapping the transaction list
        ScrollPane transactionScrollPane = new ScrollPane(transactionVBox);
        transactionScrollPane.setFitToWidth(true);
        transactionScrollPane.setFitToHeight(true);
        transactionScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        transactionScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        transactionScrollPane.getStyleClass().add("transparent-scrollpane");
        return  null;
    }
}
