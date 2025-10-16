package org.example.utils;

import javafx.scene.Scene;
import javafx.stage.Stage;



/*
 *  ViewNavigator is a utility class responsible for manging the navigation,
 *  between different scenes within the same primary stage (window) if javaFX application.
 *  It provides methods to set the main stage and switch between views (Scenes).
 */

public class ViewNavigator {
    private static Stage mainstage;

    public static void setMainStage(Stage stage){
        mainstage = stage;
    }

    public static void switchScene(Scene scene){
        if(mainstage != null){
            mainstage.setScene(scene);
            mainstage.show();
        }
    }
}
