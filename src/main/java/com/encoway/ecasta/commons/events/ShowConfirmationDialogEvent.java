package com.encoway.ecasta.commons.events;

import java.util.UUID;

/**
 * Event to show a confirmation dialog.
 * 
 * @author azzouz
 */
public class ShowConfirmationDialogEvent {

    private final UUID id;
    private final String name;

    public ShowConfirmationDialogEvent(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UUID getItem() {
        return id;
    }
}
