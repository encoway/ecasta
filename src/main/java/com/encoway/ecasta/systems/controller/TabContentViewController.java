package com.encoway.ecasta.systems.controller;

import com.encoway.ecasta.commons.controller.AbstractController;
import com.encoway.ecasta.commons.events.ConfirmationDoneEvent;
import com.encoway.ecasta.commons.events.ShowConfirmationDialogEvent;
import com.encoway.ecasta.commons.utils.ContextHolder;
import com.encoway.ecasta.commons.utils.LanguageHandler;
import com.encoway.ecasta.features.Feature;
import com.encoway.ecasta.features.events.PrepareTestEvent;
import com.encoway.ecasta.features.events.RunTestEvent;
import com.encoway.ecasta.features.services.UploadFeatureService;
import com.encoway.ecasta.features.utils.FeatureFactory;
import com.encoway.ecasta.systems.Testsystem;
import com.encoway.ecasta.systems.utils.TestsystemHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles the informations of a testsystem.
 * 
 * @author azzouz
 */
public class TabContentViewController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TabContentViewController.class);

    @FXML
    private ListView<Feature> featureListView;
    @FXML
    private TextArea featureDetailView;
    @FXML
    private Text testsystemName;
    @FXML
    private Text testsystemUrl;

    private TestsystemHandler tsHandler;
    private FeatureFactory featureFactory;
    private EventBus eventBus;
    private Stage stage;
    private UploadFeatureService uploadFeatureService;
    private LanguageHandler languageHandler;
    private Tab tab;
    private Testsystem testsystem;
    private UUID idToDelete;

    /**
     * init-method.
     * 
     * @param testsystem which will be handled
     * @throws Exception will be thrown by error while view initilizing
     */
    public void init(Testsystem testsystem) throws Exception {
        this.testsystem = testsystem;
        stage = ContextHolder.getContext().getBean(Stage.class);
        featureFactory = ContextHolder.getContext().getBean(FeatureFactory.class);
        eventBus = ContextHolder.getContext().getBean(EventBus.class);
        tsHandler = ContextHolder.getContext().getBean(TestsystemHandler.class);
        uploadFeatureService = ContextHolder.getContext().getBean(UploadFeatureService.class);
        languageHandler = ContextHolder.getContext().getBean(LanguageHandler.class);
        eventBus.register(this);
        initView();
    }

    private void initView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tabView.fxml"));
            fxmlLoader.setResources(languageHandler.getDefaultResourceBundle());
            fxmlLoader.setController(this);
            setView(fxmlLoader.load());
            testsystemName.setText(testsystem.getName());
            testsystemUrl.setText(testsystem.getUrl().toString());
            featureListView.setOnMouseClicked(event -> listClicked(featureListView.getSelectionModel().getSelectedItem()));
            testsystem.getFeatures().forEach(feature -> {
                featureListView.getItems().add(feature);
            });
            tab = new Tab(testsystem.getName(), getView());
            tab.setId(testsystem.getId().toString());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * handles run button click.
     * and fires events to prepare and running tests.
     */
    @FXML
    public void runButtonClicked() {
        tsHandler.prepareFeaturesToTest(testsystem);
        eventBus.post(new PrepareTestEvent(testsystem));
        eventBus.post(new RunTestEvent(testsystem));
    }

    /**
     * handles upload botton click.
     * and starts file chooser
     */
    @FXML
    public void uploadButtonClicked() {
        List<File> files = uploadFeatureService.startImport(stage);
        for (File f : files) {
            if (f != null) {
                Feature feature = featureFactory.create(f);
                feature.getName();
                testsystem.getFeatures().add(feature);
                featureListView.getItems().add(feature);
                tsHandler.updateTestsystem(testsystem);
            }
        }
    }

    /**
     * shows the content of the selected feature.
     * 
     * @param selectedItem the selected feature
     */
    public void listClicked(Feature selectedItem) {
        testsystem.getFeatures().forEach(element -> {
            if (selectedItem != null && element.getId().equals(selectedItem.getId())) {
                featureDetailView.setText(element.getContent());
            }
        });
    }

    /**
     * handles delete button click.
     * 
     * @param item which should be deleted
     */
    public void deleteButtonClicked(Feature item) {
        idToDelete = item.getId();
        eventBus.post(new ShowConfirmationDialogEvent(idToDelete, item.getName()));
    }

    /**
     * Handles the ConfirmationDoneEvent.
     * 
     * @param event the event
     */
    @Subscribe
    public void handleConfirmationDoneEvent(ConfirmationDoneEvent event) {
        if (event.getId().equals(idToDelete)) {
            Feature toDelete = testsystem.getFeatures().stream()
                    .filter(element -> element.getId().equals(idToDelete)).findAny()
                    .get();
            featureListView.getItems().remove(toDelete);
            testsystem.getFeatures().remove(toDelete);
            tsHandler.updateTestsystem(testsystem);
        }
    }

    /**
     * toggles the feature status if checked.
     * 
     * @param item the feature which is checked
     */
    public void checkBoxClicked(Feature item) {
        item.toggle();
    }

    /**
     * updates the infobox.
     * 
     * @param name of testsystem
     * @param url of testsystem
     * @param list contains all feature files
     */
    public void updateView(String name, String url, List<Feature> list) {
        testsystemName.setText(name);
        testsystemUrl.setText(url);
        list.forEach(element -> {
            if (!featureListView.getItems().contains(element)) {
                featureListView.getItems().add(element);
            }
        });
    }

    public Tab getTab() {
        return tab;
    }
}
