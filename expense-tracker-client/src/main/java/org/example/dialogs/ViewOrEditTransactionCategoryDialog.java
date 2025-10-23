package org.example.dialogs;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.example.controllers.DashboardController;
import org.example.models.User;

public class ViewOrEditTransactionCategoryDialog extends CustomDialog{
    private DashboardController dashboardController;

    public ViewOrEditTransactionCategoryDialog(User user, DashboardController dashboardController){
        super(user);
        this.dashboardController = dashboardController;

        // Configure the dialog
        setTitle("View Categories");
        setWidth(815);
        setHeight(500);

        ScrollPane mainContainer = createMainContainerContent();
    }

    private ScrollPane createMainContainerContent(){
        VBox dialogVBox = new VBox();
        ScrollPane mainContainer = new ScrollPane(dialogVBox);



        return mainContainer;
    }



}
