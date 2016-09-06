package com.encoway.ecasta.features.events;

import com.encoway.ecasta.systems.Testsystem;

/**
 * Event will be fired when the test has been started.
 * 
 * @author azzouz
 */
public class PrepareTestEvent {

    private final Testsystem testsystem;

    public PrepareTestEvent(Testsystem testsystem) {
        this.testsystem = testsystem;
    }

    public Testsystem getTestsystem() {
        return testsystem;
    }
}
