package com.encoway.ecasta.features.utils;

import com.encoway.ExternalRunUITests;
import com.encoway.ecasta.commons.utils.PathConstants;
import com.encoway.ecasta.commons.utils.UrlConstants;
import com.encoway.ecasta.features.events.RunTestEvent;
import com.encoway.ecasta.features.events.TestfinishedEvent;
import com.encoway.ecasta.systems.Url;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.annotation.PostConstruct;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.concurrent.Task;
import org.junit.runner.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Execute the Test.
 * 
 * @author azzouz
 */
@Component
public class TestRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestRunner.class);

    @Autowired
    private EventBus eventBus;

    /**
     * init-method.
     */
    @PostConstruct
    public void init() {
        eventBus.register(this);
    }

    /**
     * handles a Runtestevent.
     * it looks for a custom jar and load it
     * finally it execute the test
     * 
     * @param event to run the test
     */
    @Subscribe
    public void handleRunTestEvent(RunTestEvent event) {
        if (loadExternalJar()) {
            setSystemProperties(event.getTestsystem().getUrl());

            Task task = new Task<Void>() {

                @Override
                protected Void call() throws Exception {
                    execute();
                    return null;
                }
            };
            Platform.runLater(task);

        } else {
            LOGGER.error("***************Kein External Jar gefunden!****************");
        }
    }

    private void execute() {
        Class<?> testClass = ExternalRunUITests.class;
        Result result = RunnerWrapper.getInstance().apply(testClass);
        LOGGER.info("TESTRESULT was successful? => " + result.wasSuccessful());
        eventBus.post(new TestfinishedEvent(result.wasSuccessful()));
    }

    private void setSystemProperties(Url url) {
        System.setProperty(UrlConstants.SYSTEM_REMOTE_PROTOCOL,
                url.getProtokoll());
        System.setProperty(UrlConstants.SYSTEM_REMOTE_APPLICATION,
                url.getHost());
        System.setProperty(UrlConstants.SYSTEM_REMOTE_APPLICATION_PORT,
                url.getPort());
        System.setProperty(UrlConstants.SYSTEM_REMOTE_CONTEXT, url.getContext());
    }

    private boolean loadExternalJar() {
        boolean loaded = false;
        File dir = new File(PathConstants.CUSTOM_JAR_PATH);
        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                loaded = false;
                Method method;
                try {
                    method = URLClassLoader.class.getDeclaredMethod("addURL",
                            new Class[] { URL.class });
                    method.setAccessible(true);
                    method.invoke(ClassLoader.getSystemClassLoader(),
                            new Object[] { f.toURI().toURL() });
                } catch (NoSuchMethodException | SecurityException
                        | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | MalformedURLException e) {
                    LOGGER.error(e.getMessage());
                }
                loaded = true;
            }
        }
        return loaded;
    }
}
