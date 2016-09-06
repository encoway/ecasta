package com.encoway.ecasta.features.controller;

import com.encoway.ecasta.commons.controller.AbstractController;
import com.encoway.ecasta.commons.utils.LanguageHandler;
import com.encoway.ecasta.features.events.PrepareTestEvent;
import com.encoway.ecasta.features.events.TestfinishedEvent;

import java.io.IOException;

import javax.annotation.PostConstruct;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller for loading dialog.
 * 
 * @author azzouz
 */
@Component
public class LoadingDialogController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadingDialogController.class);

    @Autowired
    private EventBus eventBus;
    @Autowired
    private Stage stage;
    @Autowired
    private LanguageHandler languageHandler;

    private StackPane root;

    /**
     * init-method.
     * 
     * @throws IOException when fxml file not found
     */
    @PostConstruct
    public void init() throws IOException {
        eventBus.register(this);
        try {
            setView(FXMLLoader.load(getClass().getResource("/fxml/loadingView.fxml"), languageHandler.getDefaultResourceBundle()));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new IOException();
        }
    }

    /**
     * handles {@link PrepareTestEvent}.
     * and set a gaussian blur effect
     * 
     * @param event to handle
     * @throws Exception when blur effect can't be set
     */
    @Subscribe
    public void handlePrepareForTestEvent(PrepareTestEvent event) throws Exception {
        root = (StackPane) stage.getScene().getRoot();
        root.getChildren().get(0).setEffect(new GaussianBlur());
        root.getChildren().add(getView());
        LOGGER.info("Gaussian blur is set");
    }

    /**
     * handles {@link TestfinishedEvent}.
     * and removes the gaussian blur effect
     * 
     * @param event to handle
     */
    @Subscribe
    public void handleTestFinishedEvent(TestfinishedEvent event) {
        root.getChildren().get(0).setEffect(null);
        root.getChildren().remove(getView());
        LOGGER.info("Gaussian blur is removed");
    }
}
