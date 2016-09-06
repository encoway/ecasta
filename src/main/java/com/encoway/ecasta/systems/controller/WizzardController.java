package com.encoway.ecasta.systems.controller;

import com.encoway.ecasta.commons.events.ShowWizardDialogEvent;
import com.encoway.ecasta.commons.events.WizardFinishedEvent;
import com.encoway.ecasta.systems.Testsystem;
import com.encoway.ecasta.systems.dialogs.WizzardDialogView;
import com.encoway.ecasta.systems.events.EditTestsystemEvent;
import com.encoway.ecasta.systems.events.TestsystemChangedEvent;
import com.encoway.ecasta.systems.utils.TestsystemHandler;

import java.io.IOException;

import javax.annotation.PostConstruct;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller for wizard dialogs.
 * 
 * @author azzouz
 */
@Component
public class WizzardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WizzardController.class);

    @Autowired
    private EventBus eventBus;

    @Autowired
    private TestsystemHandler tsHandler;

    @Autowired
    private WizzardDialogView wizard;

    @Autowired
    private Stage stage;

    /**
     * init-method.
     */
    @PostConstruct
    public void init() {
        eventBus.register(this);
        wizard.setController(this);
    }

    /**
     * handles {@link ShowWizardDialogEvent} and shows the dialog.
     * 
     * @param event which will be handled
     * @throws IOException will be thrown when dialog initialization failed
     */
    @Subscribe
    public void handleShowWizard(ShowWizardDialogEvent event) throws IOException {
        wizard.initDialog(stage, null);
        wizard.show();
    }

    /**
     * will be called when wizzard has been finished.
     * adds a new testsystem.
     * 
     * @param testsystem the {@link Testsystem} to add
     */
    public void wizzardFinished(Testsystem testsystem) {
        tsHandler.addTestsystem(testsystem);
        eventBus.post(new WizardFinishedEvent(testsystem.getId()));
        wizard.close();
        LOGGER.info(testsystem.getName() + " created");
    }

    /**
     * handles {@link EditTestsystemEvent} and shows the dialog.
     * 
     * @param event which will be handled
     * @throws IOException will be thrown when dialog initialization failed
     */
    @Subscribe
    public void handleEditTestsystemEvent(EditTestsystemEvent event) throws IOException {
        Testsystem testsystem = tsHandler.getTestsystemById(event.getTestsystemId());
        wizard.initDialog(stage, testsystem);
        wizard.show();

    }

    /**
     * will be called when wizzard has been finished.
     * updates the testsystem
     * 
     * @param testsystem the {@link Testsystem} to update
     */
    public void updateFinished(Testsystem testsystem) {
        tsHandler.updateTestsystem(testsystem);
        eventBus.post(new TestsystemChangedEvent(testsystem));
        wizard.close();
        LOGGER.info(testsystem.getName() + " updated");
    }
}
