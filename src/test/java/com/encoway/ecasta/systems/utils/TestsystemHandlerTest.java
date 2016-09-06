package com.encoway.ecasta.systems.utils;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.encoway.ecasta.commons.services.FileDeletionService;
import com.encoway.ecasta.features.Feature;
import com.encoway.ecasta.features.services.FeatureFileService;
import com.encoway.ecasta.systems.Testsystem;
import com.encoway.ecasta.systems.services.TestsystemFileService;
import com.encoway.ecasta.systems.utils.TestsystemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;

@RunWith(MockitoJUnitRunner.class)
public class TestsystemHandlerTest {

    @Mock
    private List<Testsystem> list;
    @Mock
    private EventBus eventBus;
    @Mock
    private Map<UUID, Testsystem> tsMap;
    @Mock
    private TestsystemFileService testsystemFileService;
    @Mock
    private FileDeletionService fileDeletionService;
    @Mock
    private FeatureFileService featureFileService;
    @InjectMocks
    private TestsystemHandler classUndertest;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testInitilize() throws Exception {
        List<Testsystem> testsystemList = prepareList();
        when(testsystemFileService.loadTestsystems()).thenReturn(testsystemList);
        classUndertest.init();
        assertTrue(classUndertest.getTestsystems().size() == testsystemList.size());

    }

    private List<Testsystem> prepareList() {
        List<Testsystem> systemList = new ArrayList<Testsystem>();

        systemList = new ArrayList<Testsystem>();
        Testsystem ts1 = mock(Testsystem.class);
        Testsystem ts2 = mock(Testsystem.class);
        systemList.add(ts1);
        systemList.add(ts2);
        when(ts1.getId()).thenReturn(UUID.randomUUID());
        when(ts2.getId()).thenReturn(UUID.randomUUID());
        return systemList;
    }

    @Test
    public void testPrepareFeaturesToTest() throws Exception {
        Testsystem ts = prepareTestsystem();
        classUndertest.prepareFeaturesToTest(ts);
        verify(fileDeletionService, times(1)).clear(anyString());
    }

    private Testsystem prepareTestsystem() {
        Testsystem ts = mock(Testsystem.class);
        when(ts.getName()).thenReturn("Testsystem");
        List<Feature> features = new ArrayList<Feature>();
        Feature f1 = mock(Feature.class);
        when(f1.isSelected()).thenReturn(true);
        when(f1.getName()).thenReturn("TestFeature");
        when(f1.getContent()).thenReturn("TestContent");
        Feature f2 = mock(Feature.class);
        features.add(f1);
        features.add(f2);
        when(ts.getFeatures()).thenReturn(features);
        return ts;
    }

    @Test
    public void testDeleteTestsystemPass() throws Exception {
        UUID id = PowerMockito.mock(UUID.class);
        Testsystem ts = prepareTestsystem();
        when(tsMap.get(id)).thenReturn(ts);
        when(fileDeletionService.deleteFile(anyString(), anyString(), anyString())).thenReturn(true);
        classUndertest.deleteTestsystem(id);
        verify(fileDeletionService, times(3)).deleteFile(anyString(), anyString(), anyString());
    }

    @Test
    public void testDeleteTestsystemFailure() throws Exception {
        UUID id = UUID.randomUUID();
        Testsystem ts = prepareTestsystem();
        when(tsMap.get(id)).thenReturn(ts);
        when(fileDeletionService.deleteFile(anyString(), anyString(), anyString())).thenReturn(false);
        classUndertest.deleteTestsystem(id);
        verify(list, never()).remove(ts);
    }

    @Test
    public void testAddTestsystem() throws Exception {
        Testsystem ts = mock(Testsystem.class);
        UUID id = UUID.randomUUID();
        when(ts.getId()).thenReturn(id);
        when(ts.getName()).thenReturn("Test");
        classUndertest.addTestsystem(ts);
        verify(tsMap).put(ts.getId(), ts);
        verify(list).add(ts);
        verify(testsystemFileService, times(1)).saveAsJson(ts);
    }

    @Test
    public void testUpdateTestsystem() throws Exception {
        Testsystem ts = mock(Testsystem.class);
        when(ts.getId()).thenReturn(UUID.randomUUID());
        when(tsMap.get(ts.getId())).thenReturn(ts);
        classUndertest.updateTestsystem(ts);
        verify(testsystemFileService, times(1)).update(ts, ts);
    }

    @Test
    public void testGetTestsystemById() throws Exception {
        Testsystem ts = mock(Testsystem.class);
        when(ts.getId()).thenReturn(UUID.randomUUID());
        UUID id = ts.getId();
        tsMap.put(id, ts);
        assertTrue(classUndertest.getTestsystemById(id) == tsMap.get(id));
    }

}
