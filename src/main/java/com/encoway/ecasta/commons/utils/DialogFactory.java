package com.encoway.ecasta.commons.utils;

import com.encoway.ecasta.commons.dialogs.ConfirmationAlert;

import org.springframework.stereotype.Component;

/**
 * Factory for dialogs.
 */
@Component
public class DialogFactory {

    /**
     * creates a new confiirmation dialog.
     * 
     * @return an object of {@link ConfirmationAlert}
     */
    public ConfirmationAlert createConfirmDialog() {
        return new ConfirmationAlert();
    }
}
