package com.encoway.ecasta.commons.services;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.encoway.ecasta.commons.services.FileDeletionService;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FileDeletionServiceTest {

    private FileDeletionService classUnderTest = new FileDeletionService();

    @Test
    public void testDeleteFile() throws Exception {
        File file = new File("test.txt");
        FileOutputStream output = new FileOutputStream(file);
        output.close();
        boolean result = classUnderTest.deleteFile(file.getAbsolutePath(), "", "");
        assertThat(result, is(true));
    }

    @Test
    public void testClear() throws Exception {
        File file = new File("test");
        file.mkdir();
        for (int i = 0; i < 10; i++) {
            File f = new File(file.getAbsolutePath() + "//" + "test" + i + ".txt");
            FileOutputStream output = new FileOutputStream(f);
            output.close();
        }

        boolean result = classUnderTest.clear(file.getAbsolutePath());
        assertThat(result, is(true));
    }

    @Test
    public void testClearFalse() throws Exception {
        File file = new File("test.txt");
        FileOutputStream output = new FileOutputStream(file);
        output.close();
        boolean result = classUnderTest.clear(file.getAbsolutePath());
        assertThat(result, is(false));
    }
}
