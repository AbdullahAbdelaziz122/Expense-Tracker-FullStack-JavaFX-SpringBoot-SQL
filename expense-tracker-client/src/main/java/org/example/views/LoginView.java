package org.example.views;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.controllers.LoginController;
import org.example.utils.Utility;
import org.example.utils.ViewNavigator;

public class LoginView {
    private Label expenseTrackerLabel = new Label("Expense Tracker");
    private TextField usernameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Button loginButton = new Button("Login");
    private Label signupLabel = new Label("Don't have an account? Click here");

    public void show(){
        Scene scene = createScene();
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        new LoginController(this);
        ViewNavigator.switchScene(scene);
    }

    private Scene createScene(){
        VBox mainContainerBox = new VBox(71);
        mainContainerBox.getStyleClass().addAll("main-background");
        mainContainerBox.setAlignment(Pos.TOP_CENTER);

        // Login Form Box
        VBox loginFormBox = createLoginFormBox();

        // Styling expenseTrackerLabel
        expenseTrackerLabel.getStyleClass().addAll("header", "text-white");


        mainContainerBox.getChildren().addAll(expenseTrackerLabel, loginFormBox);
        return  new Scene(mainContainerBox, Utility.APP_WIDTH, Utility.APP_HEIGHT);
    }


    private VBox createLoginFormBox(){
        VBox loginFormBox = new VBox(51);

        usernameField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-borders");
        usernameField.setMaxWidth(473);
        usernameField.setPromptText("Enter Username");

        passwordField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-borders");
        passwordField.setMaxWidth(473);
        passwordField.setPromptText("Enter Password");
        loginButton.getStyleClass().addAll("text-size-lg", "bg-light-blue", "text-white", "text-weight-700", "rounded-border");
        loginButton.setMaxWidth(473);

        signupLabel.getStyleClass().addAll("text-size-md", "text-light-gray", "text-underline", "link-text");

        loginFormBox.getChildren().addAll(usernameField, passwordField, loginButton, signupLabel);
        loginFormBox.setAlignment(Pos.CENTER);
        return loginFormBox;
    }



    // getters and setters
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

    public TextField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(Button loginButton) {
        this.loginButton = loginButton;
    }

    public Label getSignupLabel() {
        return signupLabel;
    }

    public void setSignupLabel(Label signupLabel) {
        this.signupLabel = signupLabel;
    }
}
