package com.encoway.ecasta.commons.events;

import java.util.UUID;

/**
 * Event will be fired when the confirmation dialog has been confirmed.
 * 
 * @author azzouz
 */
public class ConfirmationDoneEvent {

    private final UUID id;

    public ConfirmationDoneEvent(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
