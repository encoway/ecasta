package com.encoway.ecasta.commons.dialogs;

import com.encoway.ecasta.commons.controller.DeleteConfirmationDialogViewController;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * Comfirmation Alert Dialog.
 */
public class ConfirmationAlert {

    private DeleteConfirmationDialogViewController controller;

    public void setViewListener(DeleteConfirmationDialogViewController controller) {
        this.controller = controller;
    }

    /**
     * initilize dialog.
     * 
     * @param stage where to show dialog
     * @param title of the window
     * @param headerText header information text
     * @param contentText content information text
     */
    public void initDialog(Stage stage, String title, String headerText, String contentText) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            controller.okButtonClicked();
        }
    }

}
