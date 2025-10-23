package org.example.dialogs;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.example.models.User;

public class CustomDialog extends Dialog {
    private User user;
    public CustomDialog(User user){
        this.user = user;

        // add styles sheet
        getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        getDialogPane().getStyleClass().add("main-background");
        getDialogPane().getButtonTypes().add(ButtonType.OK);

        // Button to enable closing the dialog pane
        Button buttonOk = (Button) getDialogPane().lookupButton(ButtonType.OK);
        buttonOk.setVisible(false);
        buttonOk.setDisable(true);
    }
}
