package org.example.dialogs;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.example.components.TransactionComponent;
import org.example.controllers.DashboardController;
import org.example.models.Transaction;
import org.example.models.TransactionCategory;
import org.example.models.User;
import org.example.utils.SqlUtil;
import org.example.utils.Utility;

import java.time.LocalDate;
import java.util.List;

public class CreateOrEditTransactionDialog extends CustomDialog{

    private User user;
    private List<TransactionCategory> transactionCategoryList;

    private TextField transactionNameField, transactionAmountField;
    private DatePicker transactionDatePicker;
    private ComboBox<String> transactionCategoryBox;
    private ToggleGroup transactionTypeToggleGroup;

    private boolean isEditing;
    private DashboardController dashboardController;
    private TransactionComponent transactionComponent;

    public CreateOrEditTransactionDialog(DashboardController dashboardController,
                                         TransactionComponent transactionComponent,
                                         boolean isEditing) {
        super(dashboardController);

        this.dashboardController = dashboardController;
        this.user = dashboardController.getUser();
        this.isEditing = isEditing;
        this.transactionComponent = transactionComponent;

        setTitle(isEditing ? "Editing Transaction" : "Create New Transaction");
        setHeight(595);
        setWidth(700);

        this.transactionCategoryList = SqlUtil.getAllTransactionCategoriesByUser(user.getId());
        VBox mainContainer = createMainContainer();

        getDialogPane().setContent(mainContainer);
    }

    private VBox createMainContainer(){
        VBox mainContainerVBox = new VBox(30);
        mainContainerVBox.setAlignment(Pos.CENTER);
        mainContainerVBox.getStyleClass().addAll("main-background");
//        mainContainerVBox.setMaxHeight(590);

        transactionNameField = new TextField();
        transactionNameField.setPromptText("Enter Name");
        transactionNameField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-md", "rounded-border");

        transactionAmountField = new TextField();
        transactionAmountField.setPromptText("Enter Amount");
        transactionAmountField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-md", "rounded-border");

        transactionDatePicker = new DatePicker();
        transactionDatePicker.setPromptText("Enter Date");
        transactionDatePicker.getStyleClass().addAll("field-background", "text-light-gray", "text-size-md", "rounded-border");
        transactionDatePicker.setMaxWidth(Double.MAX_VALUE);

        transactionCategoryBox = new ComboBox<>();
        transactionCategoryBox.setPromptText("Choose Category");
        transactionCategoryBox.getStyleClass().addAll("field-background", "text-light-gray", "text-size-md", "rounded-border");
        transactionCategoryBox.setMaxWidth(Double.MAX_VALUE);

        for (TransactionCategory category : transactionCategoryList){
            transactionCategoryBox.getItems().add(category.getCategoryName());
        }

        if (isEditing){
            Transaction transaction = transactionComponent.getTransaction();
            transactionNameField.setText(transaction.getName());
            transactionAmountField.setText(String.valueOf(transaction.getAmount()));
            transactionDatePicker.setValue(transaction.getDate());
            transactionCategoryBox.setValue(transaction.getCategory() == null ? "Undefined": transaction.getCategory().getCategoryName());

        }

        mainContainerVBox.getChildren()
                .addAll(transactionNameField
                        ,transactionAmountField
                        ,transactionDatePicker
                        ,transactionCategoryBox
                        ,createTransactionRadioButtonToggleGroup()
                        ,createSaveAndCancelButtons());

        return mainContainerVBox;
    }

    private HBox createTransactionRadioButtonToggleGroup(){
        HBox radioButtonBox = new HBox(50);
        radioButtonBox.setAlignment(Pos.CENTER);

        transactionTypeToggleGroup = new ToggleGroup();

        RadioButton incomeRadioButton = new RadioButton("Income");
        incomeRadioButton.setToggleGroup(transactionTypeToggleGroup);
        incomeRadioButton.getStyleClass().addAll("text-size-md", "text-light-gray");

        RadioButton expenseRadioButton = new RadioButton("Expense");
        expenseRadioButton.setToggleGroup(transactionTypeToggleGroup);
        expenseRadioButton.getStyleClass().addAll("text-size-md", "text-light-gray");

        if (isEditing){
            Transaction transaction = transactionComponent.getTransaction();
            if(transaction.getType().equalsIgnoreCase("Income")){
                incomeRadioButton.setSelected(true);
            }else{
                expenseRadioButton.setSelected(true);
            }
        }
        radioButtonBox.getChildren().addAll(incomeRadioButton, expenseRadioButton);
        return radioButtonBox;
    }


    private HBox createSaveAndCancelButtons(){
        HBox saveAndCancelButtonBox = new HBox(50);
        saveAndCancelButtonBox.setAlignment(Pos.CENTER);

        Button saveButton = new Button("Save");
        saveButton.prefWidth(200);
        saveButton.getStyleClass().addAll("bg-light-blue", "text-size-md", "text-white", "rounded-border");
        saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (validate()){
                    // Extract Data

                    String name = transactionNameField.getText();
                    Double amount = Double.parseDouble(transactionAmountField.getText());
                    LocalDate date = transactionDatePicker.getValue();
                    String type = getSelectedToggleType();
                    String categoryName = transactionCategoryBox.getValue();
                    Long categoryId = findCategoryByName( transactionCategoryList, categoryName);

                    System.out.println("Ready for post");

                    if(!isEditing) {
                        if (SqlUtil.postTransaction(user.getId(), categoryId, name, amount, date, type)) {
                            Utility.showAlertDialog(Alert.AlertType.INFORMATION, "Transaction created Successfully");
                            dashboardController.refreshDashboardData();
                            return;
                        }
                    }else{

                        if(SqlUtil.putTransaction(transactionComponent.getTransaction().getId(), name, amount, type, date, categoryId)){
                            Utility.showAlertDialog(Alert.AlertType.INFORMATION, "Transaction Updated Successfully");
                            dashboardController.refreshDashboardData();
                            return;
                        }
                    }
                }
                Utility.showAlertDialog(Alert.AlertType.ERROR, isEditing ? "Failed to update Transaction": "Failed to create Transaction");
            }
        });


        Button cancelButton = new Button("Cancel");
        cancelButton.prefWidth(200);
        cancelButton.getStyleClass().addAll("text-size-md", "rounded-border");
        cancelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                CreateOrEditTransactionDialog.this.close();
            }
        });
        saveAndCancelButtonBox.getChildren().addAll(saveButton, cancelButton);
        return saveAndCancelButtonBox;
    }

    private String getSelectedToggleType() {
        Toggle selectedToggle = transactionTypeToggleGroup.getSelectedToggle();
        String type = "";

        if (selectedToggle != null) {
            RadioButton selectedRadioButton = (RadioButton) selectedToggle;

            type = selectedRadioButton.getText();
        }
        return type;
    }

    /**
     * Validates all transaction fields.
     */
    private boolean validate(){
        // 1. Validate Name
        if(transactionNameField.getText().isEmpty()){
            error("Name field can't be empty");
            return false;
        }

        // 2. Validate Amount
        String amountText = transactionAmountField.getText();
        if(amountText.isEmpty()){
            error("Amount field can't be empty");
            return false;
        }
        try {
            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                error("Amount must be greater than zero");
                return false;
            }
        } catch (NumberFormatException e) {
            error("Enter a valid amount (e.g., 123.45)");
            return false;
        }

        // 3. Validate Date
        if (transactionDatePicker.getValue() == null) {
            error("Please select a date");
            return false;
        }

        // 4. Validate Type
        String type = getSelectedToggleType();
        if (type == null || type.isEmpty()) {
            error("Please select a type (Income or Expense)");
            return false;
        }

        if (transactionCategoryBox.getValue() == null) {
            error("Please choose a category");
            return false;
        }

        // All checks passed
        return true;
    }

    private void error(String message){
        Utility.showAlertDialog(Alert.AlertType.ERROR, message);
    }

    private Long findCategoryByName(List<TransactionCategory> transactionCategories, String name){
        for (TransactionCategory category : transactionCategories){
            if (category.getCategoryName().equals(name)){
                return category.getId();
            }
        }
        return null;
    }
}
