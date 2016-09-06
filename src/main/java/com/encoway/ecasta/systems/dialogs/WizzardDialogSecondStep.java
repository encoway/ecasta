package com.encoway.ecasta.systems.dialogs;

import com.encoway.ecasta.commons.utils.ContextHolder;
import com.encoway.ecasta.features.Feature;
import com.encoway.ecasta.features.services.UploadFeatureService;
import com.encoway.ecasta.features.utils.FeatureFactory;
import com.encoway.ecasta.systems.Testsystem;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Second dialog step to add or edit a testsystem.
 */
public class WizzardDialogSecondStep {

    @FXML
    private ListView<Feature> listView;

    private FeatureFactory featureFactory;
    private UploadFeatureService uploadFeatureService;

    private Stage stage;
    private Testsystem testsystem;
    private WizzardDialogView parent;

    /**
     * init second step to upload feature files.
     * 
     * @param parent wizzardpanel
     * @param testsystem where to add feature files
     * @param stage needed to show the file chooser
     * @param resourceBundle needed to handle languages
     * @return the content to show
     */
    public Node init(WizzardDialogView parent, Testsystem testsystem, Stage stage, ResourceBundle resourceBundle) {
        try {
            this.stage = stage;
            this.testsystem = testsystem;
            this.parent = parent;
            featureFactory = ContextHolder.getContext().getBean(FeatureFactory.class);
            uploadFeatureService = ContextHolder.getContext().getBean(UploadFeatureService.class);
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(resourceBundle);
            loader.setController(this);
            VBox content = loader.load(getClass().getResourceAsStream("/fxml/WizardDialogFeatureUploadStep.fxml"));
            return content;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * handles a click on the upload button.
     */
    @FXML
    public void uploadButtonClicked() {
        List<File> files = uploadFeatureService.startImport(stage);
        for (File file : files) {
            if (file != null) {
                Feature f = featureFactory.create(file);
                testsystem.addToList(f);
                listView.getItems().add(f);
            }
        }
    }

    /**
     * finish wizzard.
     */
    @FXML
    public void finish() {
        parent.completeWizzard();
    }
}
