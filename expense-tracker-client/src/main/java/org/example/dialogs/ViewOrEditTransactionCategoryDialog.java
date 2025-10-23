package org.example.dialogs;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.example.components.CategoryComponent;
import org.example.controllers.DashboardController;
import org.example.models.TransactionCategory;
import org.example.models.User;
import org.example.utils.SqlUtil;

import java.util.List;

public class ViewOrEditTransactionCategoryDialog extends CustomDialog{
    private DashboardController dashboardController;
    private User user;

    public ViewOrEditTransactionCategoryDialog(User user, DashboardController dashboardController){
        super(user);
        this.user = user;
        this.dashboardController = dashboardController;

        // Configure the dialog
        setTitle("View Categories");
        setWidth(815);
        setHeight(500);

        ScrollPane mainContainer = createMainContainerContent();
        getDialogPane().setContent(mainContainer);
    }

    private ScrollPane createMainContainerContent(){
        VBox dialogVBox = new VBox();
        ScrollPane mainContainer = new ScrollPane(dialogVBox);

        List<TransactionCategory> categoryList = SqlUtil.getAllTransactionCategoriesByUser(user.getId());
        for(TransactionCategory category : categoryList){
           CategoryComponent newCategory = new CategoryComponent(dashboardController, category);
           dialogVBox.getChildren().add(newCategory);
        }

        return mainContainer;
    }



}
