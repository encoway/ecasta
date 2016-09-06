package com.encoway.ecasta.systems;

import com.encoway.ecasta.features.Feature;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Testsystem contains basically a name and a URL.
 * 
 * @author azzouz
 */
public class Testsystem {

    public static final String FILE_EXTENSION = ".json";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String URL = "url";
    public static final String FEATUERES = "features";

    private final UUID id;
    private String name;
    private Url url;
    private List<Feature> features;

    /**
     * Creates a new testsystem.
     * 
     * @param id of the tesystem
     * @param name of the testsystem
     * @param url of the testsystem
     */
    public Testsystem(UUID id, String name, Url url) {
        this.id = id;
        this.name = name;
        this.url = url;
        features = new ArrayList<>();
    }

    public Testsystem() {
        this(UUID.randomUUID(), "", new Url("", "", "", ""));
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * adds a feature object to the list.
     * 
     * @param f ia a feature object
     */
    public void addToList(Feature f) {
        features.add(f);
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Url getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return name;
    }
}
