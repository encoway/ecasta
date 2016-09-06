package com.encoway.ecasta.systems.events;

import java.util.UUID;

/**
 * Event to delet a testsystem.
 * 
 * @author azzouz
 */
public class TestsystemDeletedEvent {

    private final UUID id;

    public TestsystemDeletedEvent(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
