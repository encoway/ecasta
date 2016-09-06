package com.encoway.ecasta.systems.events;

import com.encoway.ecasta.systems.Testsystem;

/**
 * Event to change a testsystem.
 * 
 * @author azzouz
 */
public class TestsystemChangedEvent {

    private final Testsystem testsystem;

    public TestsystemChangedEvent(Testsystem testsystem) {
        this.testsystem = testsystem;
    }

    public Testsystem getTestsystem() {
        return testsystem;
    }
}
