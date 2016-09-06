package com.encoway.ecasta.commons.controller;

import com.encoway.ecasta.commons.dialogs.ConfirmationAlert;
import com.encoway.ecasta.commons.events.ConfirmationDoneEvent;
import com.encoway.ecasta.commons.events.ShowConfirmationDialogEvent;
import com.encoway.ecasta.commons.utils.DialogConstants;
import com.encoway.ecasta.commons.utils.DialogFactory;
import com.encoway.ecasta.commons.utils.LanguageHandler;
import com.encoway.ecasta.features.Feature;
import com.encoway.ecasta.systems.Testsystem;

import java.util.UUID;

import javax.annotation.PostConstruct;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The controller handles user actions for the deletion of {@link Testsystem}s and {@link Feature} files.
 * 
 * @author azzouz
 */
@Component
public class DeleteConfirmationDialogViewController {

    @Autowired
    private EventBus eventBus;

    @Autowired
    private Stage stage;

    @Autowired
    private LanguageHandler languageHandler;

    @Autowired
    private DialogFactory dialogFactory;

    private UUID itemToDelete;

    @PostConstruct
    public void init() {
        eventBus.register(this);
    }

    /**
     * The method initializes the dialog to be shown.
     * 
     * @param event The event contains the data of the item to be deleted.
     */
    @Subscribe
    public void handleShowConfirmationDialog(ShowConfirmationDialogEvent event) {
        this.itemToDelete = event.getItem();
        ConfirmationAlert view = dialogFactory.createConfirmDialog();
        view.setViewListener(this);
        if (event.getName().contains(".feature")) {
            view.initDialog(stage, languageHandler.getMessage(DialogConstants.FEATURE_TITLE_TEXT),
                    String.format(languageHandler.getMessage(DialogConstants.FEATURE_HEADER_TEXT), event.getName()), DialogConstants.EMPTY);
        } else {
            view.initDialog(stage, languageHandler.getMessage(DialogConstants.TESTSYSTEM_TITLE_TEXT),
                    String.format(languageHandler.getMessage(DialogConstants.TESTSYSTEM_HEADER_TEXT), event.getName()),
                    languageHandler.getMessage(DialogConstants.TESTSYSTEM_CONTENT_TEXT));
        }
    }

    /**
     * The method is called when the user confirms the dialog.
     */
    public void okButtonClicked() {
        eventBus.post(new ConfirmationDoneEvent(itemToDelete));
    }
}
