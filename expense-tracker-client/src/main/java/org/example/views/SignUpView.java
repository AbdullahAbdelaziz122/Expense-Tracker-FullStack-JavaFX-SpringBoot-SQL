package org.example.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.utils.Utility;
import org.example.utils.ViewNavigator;

public class SignUpView {
    private Label expenseTrackerLabel = new Label("Expense Tracker");
    private TextField usernameField = new TextField();
    private TextField emailFiled = new TextField();
    private PasswordField passwordField = new PasswordField();
    private PasswordField passwordVerifyField = new PasswordField();
    private Button registerButton = new Button("Register");
    private Label loginLabel = new Label("Already have an account? Login Here");

    public void show(){
        Scene scene = createScene();
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        ViewNavigator.switchScene(scene);
    }

    private Scene createScene(){
        VBox mainContainerBox = new VBox(71);
        mainContainerBox.getStyleClass().addAll("main-background");
        mainContainerBox.setAlignment(Pos.TOP_CENTER);

        // Sign Up Form Box
        VBox signUpFormBox = createSignUpFormBox();

        // Styling expenseTrackerLabel
        expenseTrackerLabel.getStyleClass().addAll("header", "text-white");

        mainContainerBox.getChildren().addAll(expenseTrackerLabel, signUpFormBox);
        return new Scene(mainContainerBox, Utility.APP_WIDTH, Utility.APP_HEIGHT);
    }

    private VBox createSignUpFormBox(){

        VBox signUpFormBox  = new VBox(51);

        usernameField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-borders");
        usernameField.setMaxWidth(473);
        usernameField.setPromptText("Enter Username");

        emailFiled.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-borders");
        emailFiled.setMaxWidth(473);
        emailFiled.setPromptText("Enter Email");

        passwordField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-borders");
        passwordField.setMaxWidth(473);
        passwordField.setPromptText("Enter Password");

        passwordVerifyField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-borders");
        passwordVerifyField.setMaxWidth(473);
        passwordVerifyField.setPromptText("Re-Enter Password");

        registerButton.getStyleClass().addAll("text-size-lg", "bg-light-blue", "text-white", "text-weight-700", "rounded-border");
        registerButton.setMaxWidth(473);

        loginLabel.getStyleClass().addAll("text-size-md", "text-light-gray", "text-underline", "link-text");
        signUpFormBox.getChildren().addAll(usernameField, emailFiled, passwordField, passwordVerifyField, registerButton, loginLabel);
        signUpFormBox.setAlignment(Pos.CENTER);
        return signUpFormBox;
    }


    public Label getLoginLabel() {
        return loginLabel;
    }

    public void setLoginLabel(Label loginLabel) {
        this.loginLabel = loginLabel;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public void setRegisterButton(Button registerButton) {
        this.registerButton = registerButton;
    }

    public PasswordField getPasswordVerifyField() {
        return passwordVerifyField;
    }

    public void setPasswordVerifyField(PasswordField passwordVerifyField) {
        this.passwordVerifyField = passwordVerifyField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public TextField getEmailFiled() {
        return emailFiled;
    }

    public void setEmailFiled(TextField emailFiled) {
        this.emailFiled = emailFiled;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public void setUsernameField(TextField usernameField) {
        this.usernameField = usernameField;
    }

    public Label getExpenseTrackerLabel() {
        return expenseTrackerLabel;
    }

    public void setExpenseTrackerLabel(Label expenseTrackerLabel) {
        this.expenseTrackerLabel = expenseTrackerLabel;
    }
}
