package com.encoway.ecasta.features.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * handles all feature files.
 * 
 * @author azzouz
 */
public class FeatureFileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeatureFileService.class);
    private static final String COULD_NOT_SAVE_ERROR = "Could not save file '%s'. ";

    /**
     * creates a feature file which will be saved.
     * 
     * @param pathTo where to save
     * @param fileName name of the file
     * @param content of the file
     */
    public static void saveFile(String pathTo, String fileName, String content) {
        File dir = new File(pathTo);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(pathTo + "\\" + fileName);
        if (!file.exists()) {
            try {

                file.createNewFile();
                FileWriter fw = new FileWriter(file.getAbsolutePath());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
            } catch (IOException e) {
                String msg = String.format(COULD_NOT_SAVE_ERROR, file.getAbsolutePath());
                LOGGER.warn(msg, e);
            }
        }

    }
}
