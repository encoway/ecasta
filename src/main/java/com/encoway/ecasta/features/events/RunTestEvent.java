package com.encoway.ecasta.features.events;

import com.encoway.ecasta.systems.Testsystem;

/**
 * Event to run the tests.
 * 
 * @author azzouz
 */
public class RunTestEvent {

    private final Testsystem ts;

    public RunTestEvent(Testsystem ts) {
        this.ts = ts;
    }

    public Testsystem getTestsystem() {
        return ts;
    }

}
