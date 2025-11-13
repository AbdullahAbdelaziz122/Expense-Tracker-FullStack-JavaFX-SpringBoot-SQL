package org.example.views;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import org.example.animations.LoadingAnimationPane;
import org.example.controllers.DashboardController;
import org.example.models.MonthlyFinance;
import org.example.models.User;
import org.example.utils.Utility;
import org.example.utils.ViewNavigator;

import java.math.BigDecimal;
import java.time.Year;

public class DashboardView {
    private User user;

    private Label currentBalanceLabel, currentBalance;
    private Label totalIncomeLabel, totalIncome;
    private Label totalExpenseLabel, totalExpense;
    private MenuItem createNewCategoryMenuItem, viewCategoriesMenuItem, logOutMenuItem;

    private Button addTransactionButton, viewChartButton;
    private VBox recentTransactionVBox;
    private ScrollPane recentTransactionScrollPane;

    // Transaction summary
    private ComboBox<Integer> yearComboBox;


    // Table view
    TableView<MonthlyFinance> transactionTable;
    TableColumn<MonthlyFinance, String> monthColumn;
    TableColumn<MonthlyFinance, BigDecimal> incomeColumn;
    TableColumn<MonthlyFinance, BigDecimal> expenseColumn;

    private LoadingAnimationPane loadingAnimationPane;

    public DashboardView(User user){
        this.user = user;
        loadingAnimationPane = new LoadingAnimationPane(Utility.APP_WIDTH, Utility.APP_HEIGHT);
        currentBalanceLabel = new Label("Current Balance:");
        totalIncomeLabel = new Label("Total Income:");
        totalExpenseLabel = new Label("Total Expense:");

        addTransactionButton = new Button("+");


        currentBalance = new Label("$0.00");
        totalIncome = new Label("$0.00");
        totalExpense = new Label("$0.00");

    }

    public void show(){

        Scene scene = createScene();
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        new DashboardController(this, user);

        // check for changing in width or height before switching the scenes
        updateWidthAndHeight(scene);


        ViewNavigator.switchScene(scene);
    }

    private void updateWidthAndHeight(Scene scene) {
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                loadingAnimationPane.resizeWidth((double) t1);
            }
        });

        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                loadingAnimationPane.resizeHeight((double) t1);
            }
        });
    }

    private Scene createScene(){
        MenuBar menuBar = createMenuBar();

        VBox mainContainer = new VBox();
        mainContainer.getStyleClass().addAll("main-background");


        VBox mainContainerWrapper = new VBox(30);
        mainContainerWrapper.getStyleClass().addAll("dashboard-padding");
        VBox.setVgrow(mainContainerWrapper, Priority.ALWAYS);


        HBox balanceSummaryBox = createBalanceSummaryBox();


        GridPane gridPane = createGridPane();
        VBox.setVgrow(gridPane, Priority.ALWAYS);

        mainContainerWrapper.getChildren().addAll(balanceSummaryBox, gridPane);
        mainContainer.getChildren().addAll(menuBar, mainContainerWrapper, loadingAnimationPane);
        return new Scene(mainContainer, Utility.APP_WIDTH, Utility.APP_HEIGHT);
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();

        //set constraints to the cell of grid pane
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(50);
        gridPane.getColumnConstraints().addAll(columnConstraints, columnConstraints);

        // transaction table summary
        VBox transactionSummaryVbox = new VBox(20);
        HBox yearComboBoxAndChartButtonHBox= createYearComboBoxAndChartButtonHBox();
        VBox transactionTableSummary = createTransactionTableSummary();
        VBox.setVgrow(transactionTableSummary, Priority.ALWAYS);
        transactionSummaryVbox.getChildren().addAll(yearComboBoxAndChartButtonHBox, transactionTableSummary );

        // recent transaction
        VBox recentTransactionsVBox = createRecentTransactionsVBox();
        recentTransactionsVBox.getStyleClass().addAll("field-background", "rounded-border", "padding-10px");
        GridPane.setVgrow(recentTransactionsVBox, Priority.ALWAYS);

        gridPane.add(transactionSummaryVbox, 0, 0);
        gridPane.add(recentTransactionsVBox, 1, 0);
        return gridPane;
    }

    private VBox createTransactionTableSummary(){
        VBox vBox = new VBox();
        transactionTable = new TableView<>();
        VBox.setVgrow(transactionTable, Priority.ALWAYS);
        monthColumn = new TableColumn<>("Month");
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        monthColumn.getStyleClass().addAll("main-background", "text-size-md", "text-light-gray");

        incomeColumn = new TableColumn<>("Income");
        incomeColumn.setCellValueFactory(new PropertyValueFactory<>("income"));
        incomeColumn.getStyleClass().addAll("main-background", "text-size-md", "text-light-gray");

        expenseColumn = new TableColumn<>("Expense");
        expenseColumn.setCellValueFactory(new PropertyValueFactory<>("expense"));
        expenseColumn.getStyleClass().addAll("main-background", "text-size-md", "text-light-gray");

        transactionTable.getColumns().addAll(monthColumn, incomeColumn, expenseColumn);
        vBox.getChildren().addAll(transactionTable);
        return vBox;
    }
    private HBox createYearComboBoxAndChartButtonHBox(){
        HBox hbox = new HBox();
        yearComboBox = new ComboBox<Integer>();
        yearComboBox.getStyleClass().addAll("text-size-md");
        yearComboBox.setValue(Year.now().getValue());

        viewChartButton = new Button("view chart");
        viewChartButton.getStyleClass().addAll("field-background", "text-size-md", "text-light-gray");

        hbox.getChildren().addAll(yearComboBox, viewChartButton);
        return hbox;
    }

    private VBox createRecentTransactionsVBox() {
        // Container for the entire "Recent Transactions" section
        VBox containerVBox = new VBox();

        // Header with label + add button
        HBox headerBox = createRecentTransactionsLabelAndAddButtonBox();

        // Inner VBox that will actually hold the transaction items
        recentTransactionVBox = new VBox(10);
        recentTransactionVBox.setSpacing(8);

        // Scroll pane wrapping the transaction list
        recentTransactionScrollPane = new ScrollPane(recentTransactionVBox);
        recentTransactionScrollPane.setFitToWidth(true);
        recentTransactionScrollPane.setFitToHeight(true);
        recentTransactionScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        recentTransactionScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        recentTransactionScrollPane.getStyleClass().add("transparent-scrollpane");

        // Add header and scrollpane to the container
        containerVBox.getChildren().addAll(headerBox, recentTransactionScrollPane);

        return containerVBox;
    }


    private HBox createRecentTransactionsLabelAndAddButtonBox() {
        HBox createRecentTransactionsLabelAndAddButtonBox = new HBox();

        Label recentTransactions = new Label("Recent Transactions");
        recentTransactions.getStyleClass().addAll("text-size-lg", "text-light-gray");

        Region labelAndButtonSpaceRegion = new Region();
        HBox.setHgrow(labelAndButtonSpaceRegion, Priority.ALWAYS);

        addTransactionButton.getStyleClass().addAll("bg-light-red","text-size-md", "rounded-border", "text-white");

        createRecentTransactionsLabelAndAddButtonBox.getChildren().addAll(recentTransactions, labelAndButtonSpaceRegion, addTransactionButton);

        return createRecentTransactionsLabelAndAddButtonBox;

    }

    private MenuBar createMenuBar(){
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");

        createNewCategoryMenuItem = new MenuItem("Create Category");
        viewCategoriesMenuItem = new MenuItem("View Categories");
        logOutMenuItem = new MenuItem("Logout");
        fileMenu.getItems().addAll(createNewCategoryMenuItem, viewCategoriesMenuItem, logOutMenuItem);
        menuBar.getMenus().addAll(fileMenu);
        return menuBar;
    }

    private HBox createBalanceSummaryBox(){
        HBox balanceSummaryBox = new HBox();
        VBox currentBalanceVBox = new VBox();
        currentBalanceLabel.getStyleClass().addAll("text-size-lg", "text-light-gray");
        currentBalance.getStyleClass().addAll("text-size-lg", "text-white");
        currentBalanceVBox.getChildren().addAll(currentBalanceLabel, currentBalance);


        VBox totalIncomeVBox = new VBox();
        totalIncomeLabel.getStyleClass().addAll("text-size-lg", "text-light-gray");
        totalIncome.getStyleClass().addAll("text-size-lg", "text-white");
        totalIncomeVBox.getChildren().addAll(totalIncomeLabel, totalIncome);

        VBox totalExpenseVBox = new VBox();
        totalExpenseLabel.getStyleClass().addAll("text-size-lg", "text-light-gray");
        totalExpense.getStyleClass().addAll("text-size-lg", "text-white");
        totalExpenseVBox.getChildren().addAll(totalExpenseLabel, totalExpense);

        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        balanceSummaryBox.getChildren().addAll(currentBalanceVBox, region1, totalIncomeVBox, region2, totalExpenseVBox);

        return balanceSummaryBox;
    }

    public MenuItem getCreateNewCategoryMenuItem() {
        return createNewCategoryMenuItem;
    }

    public void setCreateNewCategoryMenuItem(MenuItem createNewCategoryMenuItem) {
        this.createNewCategoryMenuItem = createNewCategoryMenuItem;
    }

    public MenuItem getViewCategoriesMenuItem() {
        return viewCategoriesMenuItem;
    }

    public Button getAddTransactionButton() {
        return addTransactionButton;
    }

    public VBox getRecentTransactionVBox() {
        return recentTransactionVBox;
    }

    public LoadingAnimationPane getLoadingAnimationPane() {
        return loadingAnimationPane;
    }

    public MenuItem getLogOutMenuItem() {
        return logOutMenuItem;
    }

    public void setLogOutMenuItem(MenuItem logOutMenuItem) {
        this.logOutMenuItem = logOutMenuItem;
    }

    public TableView<MonthlyFinance> getTransactionTable() {
        return transactionTable;
    }

    public TableColumn<MonthlyFinance, String> getMonthColumn() {
        return monthColumn;
    }

    public TableColumn<MonthlyFinance, BigDecimal> getIncomeColumn() {
        return incomeColumn;
    }

    public TableColumn<MonthlyFinance, BigDecimal> getExpenseColumn() {
        return expenseColumn;
    }

    public ComboBox<Integer> getYearComboBox() {
        return yearComboBox;
    }
}
