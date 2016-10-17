package com.encoway.ecasta.commons.utils;

import com.encoway.ecasta.commons.dialogs.ConfirmationAlert;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory for dialogs.
 */
@Component
public class DialogFactory {

    @Autowired
    private LanguageHandler languageHandler;

    /**
     * creates a new confiirmation dialog.
     * 
     * @return an object of {@link ConfirmationAlert}
     */
    public ConfirmationAlert createConfirmDialog() {
        return new ConfirmationAlert();
    }

    /**
     * creates a new alert dialog.
     * 
     * @param type of {@link DialogType} to handle content of the alert.
     */
    public void createAlertDialog(DialogType type) {
        Alert alert = new Alert(AlertType.WARNING);
        if (type.equals(DialogType.NO_TESTSYSTEM)) {
            setContent(alert, DialogConstants.NO_TESTSYSTEM_FOUND, DialogConstants.NO_TESTSYSTEM_FOUND_HEADER, DialogConstants.NO_TESTSYSTEM_FOUND_CONTENT);
        } else if (type.equals(DialogType.TEST_FAILED)) {
            setContent(alert, DialogConstants.TEST_FAILED, DialogConstants.TEST_FAILED_HEADER, DialogConstants.TEST_FAILED_CONTENT);
        }
        alert.showAndWait();
    }

    private void setContent(Alert alert, String title, String headerText, String contentText) {
        alert.setTitle(languageHandler.getMessage(title));
        alert.setHeaderText(languageHandler.getMessage(headerText));
        alert.setContentText(languageHandler.getMessage(contentText));
    }
}
