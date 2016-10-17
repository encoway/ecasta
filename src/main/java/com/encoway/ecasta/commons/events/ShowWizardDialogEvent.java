package com.encoway.ecasta.commons.events;

/**
 * Event to show the wizard dialog.
 * 
 * @author azzouz
 */
public class ShowWizardDialogEvent {

    private final boolean firstInit;

    public ShowWizardDialogEvent(boolean firstInit) {
        this.firstInit = firstInit;
    }

    public boolean isFirstInit() {
        return firstInit;
    }
}
