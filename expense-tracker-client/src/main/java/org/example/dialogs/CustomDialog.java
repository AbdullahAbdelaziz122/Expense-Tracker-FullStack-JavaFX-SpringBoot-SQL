package org.example.dialogs;

import javafx.scene.control.Dialog;

public class CustomDialog extends Dialog {
    public CustomDialog(){
        // add styles sheet
        getDialogPane().getStyleClass().add(getClass().getResource("/style.css").toExternalForm());
        getDialogPane().getStyleClass().addAll("main-background");
    }
}
