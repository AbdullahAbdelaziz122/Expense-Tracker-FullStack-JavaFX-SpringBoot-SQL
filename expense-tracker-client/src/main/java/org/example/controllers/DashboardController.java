package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.example.models.User;
import org.example.dialogs.CreateNewTransactionCategoryDialog;
import org.example.dialogs.ViewOrEditTransactionCategoryDialog;
import org.example.views.DashboardView;

public class DashboardController {
    private DashboardView dashboardView;
    private User user;

    public DashboardController(DashboardView dashboardView, User user){
        this.dashboardView = dashboardView;
        this.user = user;

        initialize();
    }

    private void initialize(){

        addMenuActions();

    }

    private void addMenuActions(){
        dashboardView.getCreateNewCategoryMenuItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                new CreateNewTransactionCategoryDialog(user).showAndWait();
            }
        });


        dashboardView.getViewCategoriesMenuItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new ViewOrEditTransactionCategoryDialog(user, DashboardController.this).showAndWait();
            }
        });
    }

}
