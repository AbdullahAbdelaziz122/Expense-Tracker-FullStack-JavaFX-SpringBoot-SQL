package org.example.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.controllers.SignUpController;
import org.example.utils.Utility;
import org.example.utils.ViewNavigator;

public class SignUpView {
    private Label expenseTrackerLabel = new Label("Smart Spend");
    private TextField usernameField = new TextField();
    private TextField emailFiled = new TextField();
    private PasswordField passwordField = new PasswordField();
    private PasswordField passwordVerifyField = new PasswordField();
    private Button registerButton = new Button("Register");
    private Label loginLabel = new Label("Already have an account? Login Here");
    private ImageView logoImageView = new ImageView( new Image(getClass().getResourceAsStream("/images/logo.png")));
    public void show(){
        Scene scene = createScene();
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        new SignUpController(this);
        ViewNavigator.switchScene(scene);
    }

    private Scene createScene() {
        VBox mainContainerBox = new VBox(40); // reduced from 71 for better layout
        mainContainerBox.getStyleClass().addAll("main-background");
        mainContainerBox.setAlignment(Pos.CENTER);

        // --- Logo Styling ---
        logoImageView.setFitWidth(120);
        logoImageView.setPreserveRatio(true);

        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setColor(Color.rgb(0, 0, 0, 0.35));
        logoImageView.setEffect(shadow);

        // --- Header ---
        expenseTrackerLabel.getStyleClass().addAll("header", "text-white");

        VBox signUpFormBox = createSignUpFormBox();

        mainContainerBox.getChildren().addAll(
                logoImageView,
                expenseTrackerLabel,
                signUpFormBox
        );

        return new Scene(mainContainerBox, Utility.APP_WIDTH, Utility.APP_HEIGHT);
    }

    private VBox createSignUpFormBox() {
        VBox signUpFormBox = new VBox(20); // smoother than 25
        signUpFormBox.setAlignment(Pos.CENTER);

        // Username
        usernameField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-borders");
        usernameField.setMaxWidth(420);
        usernameField.setPromptText("Enter Username");

        // Email
        emailFiled.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-borders");
        emailFiled.setMaxWidth(420);
        emailFiled.setPromptText("Enter Email");

        // Password
        passwordField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-borders");
        passwordField.setMaxWidth(420);
        passwordField.setPromptText("Enter Password");

        // Password Verify
        passwordVerifyField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-borders");
        passwordVerifyField.setMaxWidth(420);
        passwordVerifyField.setPromptText("Re-enter Password");

        // Register button
        registerButton.getStyleClass().addAll("text-size-lg", "bg-light-blue", "text-white", "text-weight-700", "rounded-border");
        registerButton.setMaxWidth(420);

        // Login link
        loginLabel.getStyleClass().addAll("text-size-md", "text-light-gray", "text-underline", "link-text");

        signUpFormBox.getChildren().addAll(
                usernameField,
                emailFiled,
                passwordField,
                passwordVerifyField,
                registerButton,
                loginLabel
        );

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
