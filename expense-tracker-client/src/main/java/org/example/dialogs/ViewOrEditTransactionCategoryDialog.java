package org.example.dialogs;

import org.example.Controllers.DashboardController;
import org.example.User;

public class ViewOrEditTransactionCategoryDialog extends CustomDialog{
    private DashboardController dashboardController;

    public ViewOrEditTransactionCategoryDialog(User user, DashboardController dashboardController){
        super(user);
        this.dashboardController = dashboardController;

        // Configure the dialog
        setTitle("View Categories");
        setWidth(815);
        setHeight(500);
    }




}
