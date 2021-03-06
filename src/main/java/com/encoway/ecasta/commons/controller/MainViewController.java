package com.encoway.ecasta.commons.controller;

import com.encoway.ecasta.commons.utils.DialogConstants;
import com.encoway.ecasta.commons.utils.DialogFactory;
import com.encoway.ecasta.commons.utils.DialogType;
import com.encoway.ecasta.commons.utils.LanguageHandler;
import com.encoway.ecasta.commons.utils.StageFactory;
import com.encoway.ecasta.features.events.TestfinishedEvent;

import java.net.MalformedURLException;

import javax.annotation.PostConstruct;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * The controller for the main app.
 * 
 * @author azzouz
 */
@Controller
public class MainViewController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainViewController.class);

    @Autowired
    private StageFactory stageFactory;

    @Autowired
    private EventBus eventBus;

    @Autowired
    private LanguageHandler languageHandler;

    @Autowired
    private DialogFactory dialogFactory;

    @PostConstruct
    public void init() {
        eventBus.register(this);
    }

    /**
     * The method is called when a testrun has finished and makes sure that the result is shown.
     * 
     * @param event The event indicates that the testrun has finished.
     * @throws MalformedURLException when the results are not found
     */
    @Subscribe
    public void handleTestFinishedEvent(TestfinishedEvent event) throws MalformedURLException {
        if (event.isTestSucceed()) {
            Stage stage = stageFactory.createStage(languageHandler.getMessage(DialogConstants.TEST_RESULTS));
            stage.show();
            LOGGER.info("View with results created");
        } else {
            dialogFactory.createAlertDialog(DialogType.TEST_FAILED);
        }
    }

}
