package com.encoway.ecasta.systems.controller;

import com.encoway.ecasta.commons.controller.AbstractController;
import com.encoway.ecasta.commons.events.ConfirmationDoneEvent;
import com.encoway.ecasta.commons.events.InitializationEvent;
import com.encoway.ecasta.commons.events.ShowConfirmationDialogEvent;
import com.encoway.ecasta.commons.events.ShowWizardDialogEvent;
import com.encoway.ecasta.commons.events.WizardFinishedEvent;
import com.encoway.ecasta.systems.Testsystem;
import com.encoway.ecasta.systems.events.EditTestsystemEvent;
import com.encoway.ecasta.systems.events.ShowSelectedTestsystemEvent;
import com.encoway.ecasta.systems.events.TestsystemChangedEvent;
import com.encoway.ecasta.systems.events.TestsystemDeletedEvent;
import com.encoway.ecasta.systems.utils.TestsystemHandler;

import java.util.UUID;

import javax.annotation.PostConstruct;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * is the presenter for the NavigationView.
 * handles all intercation on the NavigationView.
 * 
 * @author azzouz
 */

@Controller
public class NavigationViewController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NavigationViewController.class);

    @Autowired
    private TestsystemHandler tsHandler;

    @Autowired
    private EventBus eventBus;

    @FXML
    private ListView<Testsystem> listView;

    private UUID idTodelete;

    /**
     * init-method.
     */
    @PostConstruct
    public void init() {
        eventBus.register(this);
    }

    /**
     * handles an InitializationEvent.
     * loads all stored testsystems
     * and show them in the NavigationView
     * and posts an event to select the
     * first testsystem in the view
     * 
     * @param event is an InitializationEvent
     */
    @Subscribe
    public void handleInitializationEvent(InitializationEvent event) {
        tsHandler.getTestsystems().forEach(
                element -> listView.getItems().add(element));
        eventBus.post(new ShowSelectedTestsystemEvent(tsHandler.getTestsystems().get(0).getId()));
        LOGGER.info("initialization finished");
    }

    /**
     * post an event to show the wizard dialog.
     */
    @FXML
    public void addTestsystemButtonClicked() {
        eventBus.post(new ShowWizardDialogEvent());
    }

    /**
     * post a ConfirmationDialogEvent.
     * 
     * @param id of the testsystem
     */
    public void deleteButtonClicked(UUID id) {
        idTodelete = id;
        eventBus.post(new ShowConfirmationDialogEvent(id, tsHandler
                .getTestsystemById(id).getName()));
    }

    /**
     * post an event to show the selected testsystem.
     * 
     * @param id testsystem
     */
    public void doubleClicked(UUID id) {
        eventBus.post(new ShowSelectedTestsystemEvent(id));
    }

    /**
     * Handles the click-event.
     * posts an event to show the editdialog
     * 
     * @param id of testsystem
     */
    public void editButtonClicked(UUID id) {
        eventBus.post(new EditTestsystemEvent(id));
    }

    /**
     * handles a WizardFinishedEvent.
     * updates the view by adding the new testsystem
     * 
     * @param event is a WizardFinishedEvent
     */
    @Subscribe
    public void handleWizardFinishedEvent(WizardFinishedEvent event) {
        Testsystem ts = tsHandler.getTestsystemById(event.getTestsystemId());
        if (ts != null) {
            listView.getItems().add(ts);
        }
    }

    /**
     * handles a ConfirmationDoneEvent.
     * removes the selected testsystem
     * 
     * @param event contains the id of the testsystem
     */
    @Subscribe
    public void handleDeleteConfirmation(ConfirmationDoneEvent event) {
        if (idTodelete != null && listView.getItems().remove(tsHandler.getTestsystemById(event.getId()))) {
            tsHandler.deleteTestsystem(event.getId());
            eventBus.post(new TestsystemDeletedEvent(event.getId()));
            idTodelete = null;
        }
    }

    /**
     * handles a TestsystemChangedEvent.
     * 
     * @param event contains the testsystem which should be updated
     */
    @Subscribe
    public void handleTestsystemChangedEvent(TestsystemChangedEvent event) {
        listView.getItems().forEach(element -> {
            if (element.getId().equals(event.getTestsystem().getId())) {
                replaceElement(event.getTestsystem(), element);
            }
        });
    }

    private void replaceElement(Testsystem testsystem, Testsystem toReplace) {
        int index = listView.getItems().indexOf(toReplace);
        listView.getItems().add(index, testsystem);
        listView.getItems().remove(toReplace);
    }

}
