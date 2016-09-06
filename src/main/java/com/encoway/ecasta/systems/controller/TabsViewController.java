package com.encoway.ecasta.systems.controller;

import com.encoway.ecasta.commons.controller.AbstractController;
import com.encoway.ecasta.systems.Testsystem;
import com.encoway.ecasta.systems.events.ShowSelectedTestsystemEvent;
import com.encoway.ecasta.systems.events.TestsystemChangedEvent;
import com.encoway.ecasta.systems.events.TestsystemDeletedEvent;
import com.encoway.ecasta.systems.utils.TestsystemHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Handles all tabs.
 * 
 * @author azzouz
 */
@Controller
public class TabsViewController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TabsViewController.class);

    @Autowired
    private EventBus eventBus;

    @Autowired
    private TestsystemHandler tsHandler;

    @FXML
    private TabPane tabPane;

    private Map<UUID, TabContentViewController> children;
    private TabContentViewController current;

    /**
     * init-method will be called after initialization.
     */
    @PostConstruct
    public void init() {
        eventBus.register(this);
        children = new HashMap<>();
    }

    /**
     * Handles event to show the selected testsystem.
     * 
     * @param event contains the id of the testsystem
     */
    @Subscribe
    public void handleShowSelectedTestsystemEvent(ShowSelectedTestsystemEvent event) {
        Testsystem ts = tsHandler.getTestsystemById(event.getTestsystemId());
        TabContentViewController controller = children.get(ts.getId());
        if (controller == null) {
            TabContentViewController tabController = addTab(ts);
            children.put(ts.getId(), tabController);
        } else if (!controller.getTab().isSelected()) {
            current = controller;
            tabPane.getSelectionModel().select(controller.getTab());
        }
    }

    private TabContentViewController addTab(Testsystem ts) {
        try {
            TabContentViewController tabController = new TabContentViewController();
            current = tabController;
            tabController.init(ts);
            Tab tab = tabController.getTab();
            tab.setOnClosed(e -> children.remove(ts.getId()));
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
            return tabController;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    /**
     * Handles changes on a testsystem.
     * 
     * @param event contains the changed testsystem
     */
    @Subscribe
    public void handleTestsystemChangedEvent(TestsystemChangedEvent event) {
        Testsystem ts = event.getTestsystem();
        if (children.get(ts.getId()) != null) {
            children.get(ts.getId()).updateView(ts.getName(),
                    ts.getUrl().toString(), ts.getFeatures());
            tabPane.getTabs().forEach(tab -> {
                if (tab.getId().equals(ts.getId().toString())) {
                    tab.setText(ts.getName());
                }
            });
        }
    }

    /**
     * Handles event when a testsystem is deleted.
     * to close the tab if is active
     * 
     * @param event contains the id of the deleted testsystem
     */
    @Subscribe
    public void handleTestsystemDeletedEvent(TestsystemDeletedEvent event) {
        Tab todelete = tabPane.getTabs().stream().filter(tab -> tab.getId().equals(event.getId())).findAny().get();
        if (todelete != null) {
            tabPane.getTabs().remove(todelete);
        }
    }

    public TabContentViewController getCurrent() {
        return current;
    }

}
