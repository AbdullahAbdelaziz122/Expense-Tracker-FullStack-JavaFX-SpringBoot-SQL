package org.example.animations;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LoadingAnimationPane extends Pane {
    private final Rectangle rectangle;
    private final Label loadingLabel;

    public LoadingAnimationPane(double screenWidth, double screenHeight){
        rectangle = new Rectangle(screenWidth, screenHeight, Color.BLACK);
        rectangle.setOpacity(0.5);

        loadingLabel = new Label("Loading...");
        loadingLabel.setLayoutX(screenWidth / 2 - 50);
        loadingLabel.setLayoutY(screenHeight /2 - 25);
        loadingLabel.getStyleClass().addAll("text-size-lg", "text-white");


        setMinSize(screenWidth, screenHeight);
        getChildren().addAll(rectangle, loadingLabel);
        setVisible(false);
        setManaged(false);

    }

    public void resizeWidth(double newWidth){
        this.rectangle.setWidth(newWidth);
        loadingLabel.setLayoutX(newWidth / 2 - 50);
    }

    public void resizeHeight(double newHeight){
        this.rectangle.setHeight(newHeight);
        loadingLabel.setLayoutY(newHeight / 2 - 25);
    }
}
