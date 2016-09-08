package com.encoway.ecasta.systems.controller;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.encoway.ecasta.features.Feature;
import com.encoway.ecasta.systems.Testsystem;
import com.encoway.ecasta.systems.Url;
import com.encoway.ecasta.systems.events.ShowSelectedTestsystemEvent;
import com.encoway.ecasta.systems.events.TestsystemChangedEvent;
import com.encoway.ecasta.systems.utils.TestsystemHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.eventbus.EventBus;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;

@RunWith(MockitoJUnitRunner.class)
public class TabsViewControllerTest {

    @Mock
    private Map<UUID, TabContentViewController> children;

    @Mock
    private TabContentViewController current;

    @Mock
    private EventBus eventBus;

    @Mock
    private TestsystemHandler tsHandler;

    @InjectMocks
    private TabsViewController classUnderTest;

    @Test
    public void testInit() throws Exception {
        classUnderTest.init();
        verify(eventBus).register(classUnderTest);
    }

    @Ignore
    @Test
    public void testHandleShowSelectedTestsystemEvent() throws Exception {
        Tab tab = PowerMockito.mock(Tab.class);
        ShowSelectedTestsystemEvent event = mock(ShowSelectedTestsystemEvent.class);
        UUID id = UUID.randomUUID();
        Testsystem ts = mock(Testsystem.class);
        TabContentViewController controller = mock(TabContentViewController.class);
        when(event.getTestsystemId()).thenReturn(id);
        when(tsHandler.getTestsystemById(id)).thenReturn(ts);
        when(ts.getId()).thenReturn(id);
        when(children.get(id)).thenReturn(controller);
        when(controller.getTab()).thenReturn(tab);
        when(tab.isSelected()).thenReturn(Boolean.TRUE);
        classUnderTest.handleShowSelectedTestsystemEvent(event);
        assertThat(classUnderTest.getCurrent(), not(controller));
    }

    @Ignore
    @Test
    public void testHandleTestsystemChangedEvent() throws Exception {
        TabPane tabPane = prepareTabPane();
        List<Tab> tabs = prepareTabPane().getTabs();
        Testsystem ts = mock(Testsystem.class);
        TestsystemChangedEvent event = mock(TestsystemChangedEvent.class);
        UUID id = UUID.randomUUID();
        Url url = new Url("http", "test", "8080", "");
        List<Feature> features = Arrays.asList(mock(Feature.class));
        when(event.getTestsystem()).thenReturn(ts);
        when(ts.getId()).thenReturn(id);
        when(ts.getName()).thenReturn("Testsystem");
        when(ts.getUrl()).thenReturn(url);
        when(ts.getFeatures()).thenReturn(features);
        when(children.get(id)).thenReturn(current);
        // when(tabPane.getTabs()).thenReturn((ObservableList<Tab>) tabs);
        classUnderTest.setView(tabPane);
        classUnderTest.handleTestsystemChangedEvent(event);
    }

    public TabPane prepareTabPane() {
        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab("t1");
        Tab tab2 = new Tab("t2");
        Tab tab3 = new Tab("t3");
        tabPane.getTabs().addAll(tab1, tab2, tab3);
        return tabPane;
    }

}
