package com.encoway.ecasta.features.utils;

import com.encoway.ecasta.features.Feature;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.UUID;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Factory to build feature object.
 * 
 * @author azzouz
 */
@Component
public class FeatureFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger("FeatureFactory");

    private static final Charset CHARSET = Charsets.UTF_8;
    private static final String COULD_NOT_READ_ERROR = "Could not read file '%s'. ";

    /**
     * creates an object of {@link Feature}.
     * 
     * @param file a feature file
     * @return an object of {@link Feature}
     */
    public Feature create(File file) {
        try {
            String content = Files.toString(file, CHARSET);
            return new Feature(UUID.randomUUID(), file.getName(), content);
        } catch (IOException e) {
            String msg = String.format(COULD_NOT_READ_ERROR, file.getAbsolutePath());
            LOGGER.error(msg, e);
            throw new RuntimeException(msg);
        }
    }
}
