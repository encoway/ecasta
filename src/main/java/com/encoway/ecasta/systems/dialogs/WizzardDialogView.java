package com.encoway.ecasta.systems.dialogs;

import com.encoway.ecasta.commons.utils.DialogConstants;
import com.encoway.ecasta.commons.utils.LanguageHandler;
import com.encoway.ecasta.systems.Testsystem;
import com.encoway.ecasta.systems.controller.WizzardController;

import java.io.IOException;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Dialog View.
 */
@Component
public class WizzardDialogView {

    @Autowired
    private LanguageHandler languageHandler;
    private Dialog<Object> dialog;
    private WizzardController controller;
    private Stage stage;

    private Testsystem testsystem;

    private boolean edit;

    /**
     * initiliaze the dialog.
     * 
     * @param stage where to show the dialog.
     * @param testsystem to store data in
     * @return {@link Dialog}
     * @throws IOException when initialization failed
     */
    public Dialog<Object> initDialog(Stage stage, Testsystem testsystem) throws IOException {
        if (testsystem != null) {
            edit = true;
            this.testsystem = new Testsystem(testsystem.getId(), testsystem.getName(), testsystem.getUrl());
            this.testsystem.getFeatures().addAll(testsystem.getFeatures());
        } else {
            testsystem = new Testsystem();
            this.testsystem = testsystem;
        }

        this.stage = stage;
        dialog = new Dialog<>();
        dialog.setHeaderText(languageHandler.getMessage(DialogConstants.DIALOG_HEADER));
        dialog.setTitle(languageHandler.getMessage(DialogConstants.DIALOG_TITLE));
        dialog.getDialogPane().setContent(new WizzardDialogFirstStep().init(this.testsystem, this, languageHandler.getDefaultResourceBundle()));
        dialog.setResizable(false);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);
        dialog.setGraphic(new ImageView(new Image("icons/info_icon-2.png")));
        return dialog;
    }

    /**
     * close the dialog.
     */
    public void close() {
        dialog.close();
    }

    /**
     * show the dialog.
     */
    public void show() {
        dialog.showAndWait();
    }

    /**
     * show the next dialog.
     */
    public void next() {
        dialog.getDialogPane().setContent(new WizzardDialogSecondStep().init(this, this.testsystem, stage, languageHandler.getDefaultResourceBundle()));
    }

    /**
     * should be called when wizzard has been finished.
     */
    public void completeWizzard() {
        if (edit) {
            controller.updateFinished(testsystem);
        } else {
            controller.wizzardFinished(testsystem);
        }
    }

    public void setController(WizzardController controller) {
        this.controller = controller;
    }

}
