package com.encoway.ecasta.features.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.encoway.ecasta.features.events.PrepareTestEvent;

import com.google.common.eventbus.EventBus;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Stage.class, Scene.class, StackPane.class, Node.class, ObservableList.class })
public class LoadingDialogControllerTest {

    @Mock
    private EventBus eventBus;

    @InjectMocks
    private LoadingDialogController classUnderTest;

    @Test
    @Ignore
    public void testHandlePrepareForTestEvent() throws Exception {
        Stage stage = PowerMockito.mock(Stage.class);
        Node node = PowerMockito.mock(Node.class);
        Scene scene = PowerMockito.mock(Scene.class);
        StackPane root = PowerMockito.mock(StackPane.class);
        ObservableList<Node> children = PowerMockito.mock(ObservableList.class);

        when(scene.getRoot()).thenReturn(root);
        when(stage.getScene()).thenReturn(scene);
        when(root.getChildren()).thenReturn(children);
        when(children.get(0)).thenReturn(node);
        classUnderTest.handlePrepareForTestEvent(any(PrepareTestEvent.class));
        verify(node).setEffect(any(GaussianBlur.class));
    }

    @Test
    public void testHandleTestFinishedEvent() throws Exception {
    }

}
