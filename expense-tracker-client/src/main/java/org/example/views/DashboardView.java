package org.example.views;


import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.example.Controllers.DashboardController;
import org.example.User;
import org.example.utils.Utility;
import org.example.utils.ViewNavigator;

public class DashboardView {
    private User user;

    private Label currentBalanceLabel, currentBalance;
    private Label totalIncomeLabel, totalIncome;
    private Label totalExpenseLabel, totalExpense;
    private MenuItem createNewCategoryMenuItem, viewCategoriesMenuItem;

    public DashboardView(User user){
        this.user = user;

        currentBalanceLabel = new Label("Current Balance:");
        totalIncomeLabel = new Label("Total Income:");
        totalExpenseLabel = new Label("Total Expense:");

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


        mainContainer.getChildren().addAll(menuBar, balanceSummaryBox);
        return new Scene(mainContainer, Utility.APP_WIDTH, Utility.APP_HEIGHT);
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
