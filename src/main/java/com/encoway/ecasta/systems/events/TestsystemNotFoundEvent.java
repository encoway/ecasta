package com.encoway.ecasta.systems.events;

import javafx.stage.Stage;

/**
 * Event when testsystem is not found.
 */
public class TestsystemNotFoundEvent {

    private final Stage stage;

    public TestsystemNotFoundEvent(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

}
