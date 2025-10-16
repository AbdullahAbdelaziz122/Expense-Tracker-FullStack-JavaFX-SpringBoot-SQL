package org.example.Controllers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
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
    }
}
