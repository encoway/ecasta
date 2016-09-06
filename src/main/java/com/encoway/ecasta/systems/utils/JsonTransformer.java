package com.encoway.ecasta.systems.utils;

import com.encoway.ecasta.features.services.FeatureFileService;
import com.encoway.ecasta.systems.Testsystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * transform a json to a testsystem.
 * 
 * @author azzouz
 */
@Component
public class JsonTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeatureFileService.class);
    private static final String COULD_NOT_READ_ERROR = "Could not find file '%s'. ";
    private static final String COULD_NOT_SAVE_ERROR = "Could not save file '%s'. ";

    /**
     * transforms a testsystem to a json object.
     * 
     * @param testsystem which will transformed to a json
     * @param pathTo where it will be saved
     */
    @SuppressWarnings("resource")
    public void transferToJson(Testsystem testsystem, String pathTo) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(testsystem);
            FileWriter writer = new FileWriter(pathTo);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            String msg = String.format(COULD_NOT_SAVE_ERROR, pathTo);
            LOGGER.warn(msg, e);
        }
    }

    /**
     * transform a json to a testsystem.
     * 
     * @param pathFrom where the json is saved
     * @return a testsystem
     */
    @SuppressWarnings("resource")
    public Testsystem getTestsystemFromJson(String pathFrom) {
        try {
            Gson gson = new Gson();
            BufferedReader br = new BufferedReader(new FileReader(pathFrom));
            Testsystem ts = gson.fromJson(br, Testsystem.class);
            return ts;

        } catch (FileNotFoundException e) {
            String msg = String.format(COULD_NOT_READ_ERROR, pathFrom);
            LOGGER.warn(msg, e);

        }
        return null;
    }

}
