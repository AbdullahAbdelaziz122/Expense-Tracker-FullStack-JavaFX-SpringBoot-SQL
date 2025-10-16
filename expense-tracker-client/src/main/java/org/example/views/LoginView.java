package org.example.views;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.Controllers.LoginController;
import org.example.utils.Utility;
import org.example.utils.ViewNavigator;

public class LoginView {
    private Label expenseTrackerLabel = new Label("Expense Tracker");
    private TextField usernameFiled = new TextField();
    private TextField passwordFiled = new TextField();
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

        usernameFiled.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-borders");
        usernameFiled.setMaxWidth(473);
        usernameFiled.setPromptText("Enter Username");

        passwordFiled.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-borders");
        passwordFiled.setMaxWidth(473);
        passwordFiled.setPromptText("Enter Password");
        loginButton.getStyleClass().addAll("text-size-lg", "bg-light-blue", "text-white", "text-weight-700", "rounded-border");
        loginButton.setMaxWidth(473);

        signupLabel.getStyleClass().addAll("text-size-md", "text-light-gray", "text-underline", "link-text");

        loginFormBox.getChildren().addAll(usernameFiled, passwordFiled, loginButton, signupLabel);
        loginFormBox.setAlignment(Pos.CENTER);
        return loginFormBox;
    }


    public TextField getUsernameFiled() {
        return usernameFiled;
    }

    public void setUsernameFiled(TextField usernameFiled) {
        this.usernameFiled = usernameFiled;
    }

    public Label getExpenseTrackerLabel() {
        return expenseTrackerLabel;
    }

    public void setExpenseTrackerLabel(Label expenseTrackerLabel) {
        this.expenseTrackerLabel = expenseTrackerLabel;
    }

    public TextField getPasswordFiled() {
        return passwordFiled;
    }

    public void setPasswordFiled(TextField passwordFiled) {
        this.passwordFiled = passwordFiled;
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
