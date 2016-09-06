package com.encoway.ecasta.commons.controller;

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
        Stage stage = stageFactory.createStage("Testergebnis");
        stage.show();
        LOGGER.info("View with results created");

    }

}
