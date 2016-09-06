package com.encoway.ecasta.systems.events;

import javafx.scene.control.Tab;

/**
 * Event will be fired when a testsystem tab is created.
 */
public class TestsystemTabCreatedEvent {

    private final Tab tab;

    public TestsystemTabCreatedEvent(Tab tab) {
        this.tab = tab;
    }

    public Tab getTab() {
        return tab;
    }
}
