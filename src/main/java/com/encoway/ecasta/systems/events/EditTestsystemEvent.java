package com.encoway.ecasta.systems.events;

import java.util.UUID;

/**
 * Event to edit a testsystem.
 * 
 * @author azzouz
 */
public class EditTestsystemEvent {

    private final UUID id;

    public EditTestsystemEvent(UUID id) {
        this.id = id;
    }

    public UUID getTestsystemId() {
        return id;
    }
}
