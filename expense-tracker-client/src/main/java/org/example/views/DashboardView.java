package org.example.views;


import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.controllers.DashboardController;
import org.example.models.User;
import org.example.utils.Utility;
import org.example.utils.ViewNavigator;

public class DashboardView {
    private User user;

    private Label currentBalanceLabel, currentBalance;
    private Label totalIncomeLabel, totalIncome;
    private Label totalExpenseLabel, totalExpense;
    private MenuItem createNewCategoryMenuItem, viewCategoriesMenuItem;

    private Button addTransactionButton;
    private VBox recentTransactionVBox;
    private ScrollPane recentTransactionScrollPane;

    public DashboardView(User user){
        this.user = user;

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
        ViewNavigator.switchScene(scene);
    }

    private Scene createScene(){
        MenuBar menuBar = createMenuBar();

        VBox mainContainer = new VBox();
        mainContainer.getStyleClass().addAll("main-background");

        HBox balanceSummaryBox = createBalanceSummaryBox();

        GridPane gridPane = createGridPane();

        mainContainer.getChildren().addAll(menuBar, balanceSummaryBox, gridPane);
        return new Scene(mainContainer, Utility.APP_WIDTH, Utility.APP_HEIGHT);
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();

        VBox recentTransactionsVBox = createRecentTransactionsVBox();

        gridPane.add(recentTransactionsVBox, 0, 1);
        return gridPane;
    }

    private VBox createRecentTransactionsVBox() {

        VBox recentTransactionsVBox = new VBox();
        HBox recentTransactionsLabelAndAddButton = createRecentTransactionsLabelAndAddButtonBox();


        // recent transactions scroll pane
        recentTransactionsVBox = new VBox();
        recentTransactionScrollPane = new ScrollPane(recentTransactionVBox);



        recentTransactionsVBox.getChildren().addAll(recentTransactionsLabelAndAddButton, recentTransactionScrollPane);
        return recentTransactionsVBox;
    }

    private HBox createRecentTransactionsLabelAndAddButtonBox() {
        HBox createRecentTransactionsLabelAndAddButtonBox = new HBox();

        Label recentTransactions = new Label("Recent Transactions");

        Region labelAndButtonSpaceRegion = new Region();
        HBox.setHgrow(labelAndButtonSpaceRegion, Priority.ALWAYS);

        createRecentTransactionsLabelAndAddButtonBox.getChildren().addAll(recentTransactions, labelAndButtonSpaceRegion, addTransactionButton);

        return createRecentTransactionsLabelAndAddButtonBox;

    }

    private MenuBar createMenuBar(){
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");

        createNewCategoryMenuItem = new MenuItem("Create Category");
        viewCategoriesMenuItem = new MenuItem("View Categories");
        fileMenu.getItems().addAll(createNewCategoryMenuItem, viewCategoriesMenuItem);
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
}
