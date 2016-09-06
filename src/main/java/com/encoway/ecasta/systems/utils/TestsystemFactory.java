package com.encoway.ecasta.systems.utils;

import com.encoway.ecasta.systems.Testsystem;
import com.encoway.ecasta.systems.Url;

import java.util.Properties;
import java.util.UUID;

/**
 * Factory to build testsystem objects.
 */
public class TestsystemFactory {

    /**
     * creates an object of {@link Testsystem}.
     * 
     * @param p a properties object
     * @return an object of {@link Testsystem}
     */
    public static Testsystem create(Properties p) {
        return new Testsystem(UUID.fromString((String) p.get(Testsystem.ID)), (String) p.get(Testsystem.NAME), (Url) p.get(Testsystem.URL));
    }

}
