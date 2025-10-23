package org.example.dialogs;

import org.example.Controllers.DashboardController;
import org.example.models.User;
import org.example.utils.SqlUtil;

public class ViewOrEditTransactionCategoryDialog extends CustomDialog{
    private DashboardController dashboardController;

    public ViewOrEditTransactionCategoryDialog(User user, DashboardController dashboardController){
        super(user);
        this.dashboardController = dashboardController;

        // Configure the dialog
        setTitle("View Categories");
        setWidth(815);
        setHeight(500);
        SqlUtil.getAllTransactionCategoriesByUser(2L);
//        ScrollPane
    }




}
