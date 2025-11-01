package org.example.controllers;

import com.google.gson.JsonObject;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import org.example.utils.ApiUtil;
import org.example.utils.SqlUtil;
import org.example.utils.Utility;
import org.example.views.LoginView;
import org.example.views.SignUpView;


public class SignUpController {
    SignUpView signUpView;

    public SignUpController(SignUpView signUpView){
        this.signUpView = signUpView;
        initialize();
    }

    public void initialize(){

        signUpView.getLoginLabel().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                new LoginView().show();
            }
        });
        signUpView.getRegisterButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (!validateUser()){
                    Utility.showAlertDialog(Alert.AlertType.ERROR, "Invalid Input");
                    return;
                }

                // Extract the data
                String username = signUpView.getUsernameField().getText();
                String email = signUpView.getEmailFiled().getText();
                String password = signUpView.getPasswordField().getText();

                if(register(username, email, password)){
                    new LoginView().show();
                }
            }
        });
    }

    private boolean validateUser(){
        if(signUpView.getUsernameField().getText().isEmpty()) return  false;
        if(!validateEmail(signUpView.getEmailFiled().getText())) return false;
        return validatePassword(signUpView.getPasswordField().getText(), signUpView.getPasswordVerifyField().getText());
    }


    private boolean validateEmail(String email){
        if(email.isEmpty()) return false;
        return Utility.isValidEmail(email);
    }

    private boolean validatePassword(String password, String passwordVerify){
        if (password.isEmpty() || passwordVerify.isEmpty()) return false;
        return password.equals(passwordVerify);
    }

    private boolean register(String username, String email, String password){
        return SqlUtil.register(username, email, password);
    }
}
