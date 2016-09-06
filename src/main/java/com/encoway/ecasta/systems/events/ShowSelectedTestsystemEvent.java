package com.encoway.ecasta.systems.events;

import java.util.UUID;

/**
 * Event to show the selected testsystem.
 * 
 * @author azzouz
 */
public class ShowSelectedTestsystemEvent {

    private final UUID id;

    public ShowSelectedTestsystemEvent(UUID id) {
        this.id = id;
    }

    public UUID getTestsystemId() {
        return id;
    }
}
