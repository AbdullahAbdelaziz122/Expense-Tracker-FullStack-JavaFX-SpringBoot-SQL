package org.example.controllers;

import com.google.gson.JsonObject;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import org.example.models.User;
import org.example.utils.ApiUtil;
import org.example.utils.SqlUtil;
import org.example.utils.Utility;
import org.example.views.DashboardView;
import org.example.views.LoginView;
import org.example.views.SignUpView;

public class LoginController {
    LoginView loginView;

    public LoginController(LoginView loginView){
        this.loginView = loginView;
        initialize();
    }

    public void initialize() {
        loginView.getLoginButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                String email = loginView.getUsernameField().getText();
                String password = loginView.getPasswordField().getText();
                User user = login(email, password);
                if(user!=null){
                    new DashboardView(user).show();
                }


            }
        });

        loginView.getSignupLabel().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                new SignUpView().show();
            }
        });
    }


    public User login(String email, String password) {

         User user = SqlUtil.login(email, password);
         return user;
    }
}