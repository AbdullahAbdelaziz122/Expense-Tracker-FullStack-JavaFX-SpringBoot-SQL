package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.models.User;
import org.example.utils.ViewNavigator;
import org.example.views.DashboardView;
import org.example.views.LoginView;
import org.example.views.SignUpView;

public class JavaFXApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ViewNavigator.setMainStage(stage);
        new LoginView().show();

//        // for Debugging
//        User fakeUser= new User();
//        fakeUser.setId(1L);
//        new DashboardView(fakeUser).show();
    }
}
