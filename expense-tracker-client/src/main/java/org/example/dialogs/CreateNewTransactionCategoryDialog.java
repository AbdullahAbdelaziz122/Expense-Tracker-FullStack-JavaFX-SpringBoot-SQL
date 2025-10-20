package org.example.dialogs;

import com.google.gson.JsonObject;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.example.User;
import org.example.utils.SqlUtil;
import org.example.utils.Utility;

public class CreateNewTransactionCategoryDialog extends CustomDialog{
    private TextField newCategoryTextFiled;
    private ColorPicker colorPicker;
    private Button createCategoryBtn;
    private User user;

    public CreateNewTransactionCategoryDialog(User user){
        super(user);
        this.user = user;
        setTitle("Create New Category");
        getDialogPane().setContent(createDialogContentBox());
    }

    private VBox createDialogContentBox(){
        VBox dialogContentBox = new VBox(30);

        newCategoryTextFiled = new TextField();
        newCategoryTextFiled.setPromptText("Enter Category Name");
        newCategoryTextFiled.getStyleClass().addAll("text-size-md", "field-background", "text-light-gray");

        colorPicker = new ColorPicker();
        colorPicker.getStyleClass().add("text-size-md");
        colorPicker.setMaxWidth(Double.MAX_VALUE);

        createCategoryBtn = new Button("Create");
        createCategoryBtn.getStyleClass().addAll("bg-light-blue","text-size-md", "text-white");
        createCategoryBtn.setMaxWidth(Double.MAX_VALUE);

        createCategoryBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                JsonObject response = SqlUtil.postTransactionCategory(
                                user.getId(),
                                newCategoryTextFiled.getText(),
                                Utility.convertHexColor(colorPicker)
                );
                handelCreateCategoryResponse(response);
            }
        });

        dialogContentBox.getChildren().addAll(newCategoryTextFiled, colorPicker, createCategoryBtn);
        return dialogContentBox;
    }

    private void handelCreateCategoryResponse(JsonObject response){
        int status = response.get("status").getAsInt();

        if(status != 201){
            Utility.showAlertDialog(Alert.AlertType.ERROR, "Create Failed Due to:\n" + response.get("error").toString());
        }
        Utility.showAlertDialog(Alert.AlertType.INFORMATION, "Category created Successfully");
    }

}
