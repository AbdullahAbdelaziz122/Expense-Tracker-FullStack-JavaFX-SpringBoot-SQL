package org.example.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.example.dialogs.CreateNewCategoryDialog;
import org.example.views.DashboardView;

public class DashboardController {
    private DashboardView dashboardView;

    public DashboardController(DashboardView dashboardView){
        this.dashboardView = dashboardView;

        initialize();
    }

    private void initialize(){

        addMenuActions();

    }

    private void addMenuActions(){
        dashboardView.getCreateNewCategoryMenuItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new CreateNewCategoryDialog().showAndWait();
            }
        });
    }
}
