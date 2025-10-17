package org.example.dialogs;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CreateNewCategoryDialog extends CustomDialog{
    private TextField newCategoryTextFiled;
    private ColorPicker colorPicker;
    private Button createCategoryBtn;

    public CreateNewCategoryDialog(){
        super();
        setTitle("Create New Category");
        getDialogPane().setContent(createDialogeContentBox());
    }

    private VBox createDialogeContentBox(){
        VBox dialogContentBox = new VBox();

        newCategoryTextFiled = new TextField();
        newCategoryTextFiled.setPromptText("Enter Category Name");
        newCategoryTextFiled.getStyleClass().addAll("text-size-md", "field-background", "text-light-gray");
        

        dialogContentBox.getChildren().addAll(newCategoryTextFiled);
        return dialogContentBox;
    }


}
