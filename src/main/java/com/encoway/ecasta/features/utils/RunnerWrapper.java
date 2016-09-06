package com.encoway.ecasta.features.utils;

import com.google.common.base.Function;
import org.junit.runner.Result;

/**
 * runs the test.
 * 
 * @author azzouz
 */
public class RunnerWrapper implements Function<Class<?>, Result> {

    private static final RunnerWrapper INSTANCE = new RunnerWrapper();

    private RunnerWrapper() {
    }

    public static Function<Class<?>, Result> getInstance() {
        return INSTANCE;
    }

    /**
     * apply the tests.
     * 
     * @param testClass defines the path to the featurefile
     * @return result of the run tests
     */
    @Override
    public Result apply(Class<?> testClass) {
        return org.junit.runner.JUnitCore.runClasses(testClass);
    }
}
