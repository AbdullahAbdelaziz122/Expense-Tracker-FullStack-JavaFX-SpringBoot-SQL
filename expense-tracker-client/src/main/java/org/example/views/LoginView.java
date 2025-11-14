package org.example.views;


import javafx.geometry.Pos;
import javafx.scene.Node;
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
import org.example.controllers.LoginController;
import org.example.utils.Utility;
import org.example.utils.ViewNavigator;


public class LoginView {
    private Label expenseTrackerLabel = new Label("Smart Spend");
    private ImageView logoImageView = new ImageView( new Image(getClass().getResourceAsStream("/images/logo.png")));
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
        VBox mainContainerBox = new VBox(40);
        mainContainerBox.getStyleClass().addAll("main-background");
        mainContainerBox.setAlignment(Pos.CENTER);

        // --- Styling the logo ---
        logoImageView.setFitWidth(120);
        logoImageView.setPreserveRatio(true);

        // Add a soft shadow under the logo
        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setColor(Color.rgb(0, 0, 0, 0.35));
        logoImageView.setEffect(shadow);

        // --- Title styling ---
        expenseTrackerLabel.getStyleClass().addAll("header", "text-white");

        VBox loginFormBox = createLoginFormBox();

        mainContainerBox.getChildren().addAll(
                logoImageView,
                expenseTrackerLabel,
                loginFormBox
        );

        return new Scene(mainContainerBox, Utility.APP_WIDTH, Utility.APP_HEIGHT);
    }


    private VBox createLoginFormBox(){
        VBox loginFormBox = new VBox(25);

        usernameField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-borders");
        usernameField.setMaxWidth(420);
        usernameField.setPromptText("Enter Username");

        passwordField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-borders");
        passwordField.setMaxWidth(420);
        passwordField.setPromptText("Enter Password");

        loginButton.getStyleClass().addAll("text-size-lg", "bg-light-blue", "text-white", "text-weight-700", "rounded-border");
        loginButton.setMaxWidth(420);

        signupLabel.getStyleClass().addAll("text-size-md", "text-light-gray", "text-underline", "link-text");

        loginFormBox.setAlignment(Pos.CENTER);
        loginFormBox.getChildren().addAll(usernameField, passwordField, loginButton, signupLabel);

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
