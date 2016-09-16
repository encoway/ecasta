package com.encoway.ecasta.features.events;

/**
 * Event will be fired when the test is finished.
 * 
 * @author azzouz
 */
public class TestfinishedEvent {

    private final boolean testSuccess;

    public TestfinishedEvent(boolean testSuccess) {
        this.testSuccess = testSuccess;
    }

    public boolean isTestSucceed() {
        return testSuccess;
    }

}
