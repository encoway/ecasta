package com.encoway.ecasta.commons.controller;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.encoway.ecasta.commons.utils.StageFactory;
import com.encoway.ecasta.features.events.TestfinishedEvent;

import com.google.common.eventbus.EventBus;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Stage.class })
public class MainViewControllerTest {

    @Mock
    private EventBus eventBus;

    @Mock
    private StageFactory stageFactory;
    @InjectMocks
    private MainViewController classUnderTest;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testInit() throws Exception {
        classUnderTest.init();
        verify(eventBus).register(classUnderTest);
    }

    @Test
    public void testHandleTestFinishedEvent() throws Exception {
        Stage stage = PowerMockito.mock(Stage.class);
        TestfinishedEvent event = new TestfinishedEvent();
        when(stageFactory.createStage(anyString())).thenReturn(stage);
        classUnderTest.handleTestFinishedEvent(event);
        verify(stage).show();

    }
}
