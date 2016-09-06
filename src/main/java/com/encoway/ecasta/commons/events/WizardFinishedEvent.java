package com.encoway.ecasta.commons.events;

import java.util.UUID;

/**
 * Event will be fired when the wizard dialog is finished.
 * 
 * @author azzouz
 */
public class WizardFinishedEvent {

    private final UUID id;

    public WizardFinishedEvent(UUID id) {
        this.id = id;
    }

    public UUID getTestsystemId() {
        return id;
    }

}
