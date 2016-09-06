package com.encoway.ecasta.systems.dialogs;

import com.encoway.ecasta.systems.Testsystem;
import com.encoway.ecasta.systems.Url;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.UUID;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * First dialog step to add or edit a testsystem.
 */
public class WizzardDialogFirstStep {

    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<String> protocollBox;
    @FXML
    private TextField hostField;
    @FXML
    private TextField portField;
    @FXML
    private TextField contextField;

    private Testsystem testsystem;

    private WizzardDialogView parent;

    /**
     * init method.
     * 
     * @param testsystem is empty if new, will be edited else.
     * @param parent WizardDialogView
     * @param resourceBundle needed to handle translations
     * @return the content to show
     */
    public Node init(Testsystem testsystem, WizzardDialogView parent, ResourceBundle resourceBundle) {
        try {
            this.testsystem = testsystem;
            this.parent = parent;
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(resourceBundle);
            loader.setController(this);
            GridPane content = loader.load(getClass().getResourceAsStream("/fxml/WizardDialogConfigureTestsystemStep.fxml"));
            protocollBox.getItems().addAll("HTTP", "HTTPS");
            checkTestsystem();
            return content;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkTestsystem() {
        if (testsystem != null) {
            nameField.setText(testsystem.getName());
            hostField.setText(testsystem.getUrl().getHost());
            portField.setText(testsystem.getUrl().getPort());
            contextField.setText(testsystem.getUrl().getContext());
            protocollBox.getSelectionModel().select(testsystem.getUrl().getProtokoll());
        }
    }

    /**
     * navigate to next dialog step.
     * and stores all given data into testsystem
     * 
     * @throws IOException when fxml file is not found
     */

    @FXML
    public void showNextPage() throws IOException {
        if (testsystem == null) {
            testsystem = new Testsystem(UUID.randomUUID(), nameField.getText(), buildUrl());
        } else {
            testsystem.setName(nameField.getText());
            testsystem.setUrl(buildUrl());
        }
        parent.next();
    }

    private Url buildUrl() {
        return new Url(protocollBox.getValue(), hostField.getText(), portField.getText(), contextField.getText());
    }

    public Testsystem getTestsystem() {
        return testsystem;
    }

}
