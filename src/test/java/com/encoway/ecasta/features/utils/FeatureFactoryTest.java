package com.encoway.ecasta.features.utils;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.encoway.ecasta.features.Feature;
import com.encoway.ecasta.features.utils.FeatureFactory;

import java.io.File;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Files.class })
public class FeatureFactoryTest {

    private FeatureFactory classUnderTest = new FeatureFactory();

    @Test
    public void testCreateSuccess() throws Exception {
        PowerMockito.mockStatic(Files.class);
        File file = mock(File.class);
        Feature feature = mock(Feature.class);
        String fileName = "Test";
        String fileContent = "TestContent";
        when(file.getName()).thenReturn(fileName);
        when(Files.toString(file, Charsets.UTF_8)).thenReturn(fileContent);
        Feature f = classUnderTest.create(file);
        assertTrue(f.getName().equals(fileName));
        assertTrue(f.getContent().equals(fileContent));
    }

}
