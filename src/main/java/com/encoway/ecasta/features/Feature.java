package com.encoway.ecasta.features;

import java.util.UUID;

/**
 * Object representation for feature files.
 * 
 * @author azzouz
 */
public class Feature {

    public static final String FILE_EXTENSION = ".feature";

    private final UUID id;
    private final String filename;

    private boolean selected;
    private String content;

    /**
     * Creates a feature file representation object.
     * 
     * @param id Id
     * @param name Filename
     * @param content Content
     */
    public Feature(UUID id, String name, String content) {
        this.id = id;
        this.filename = name;
        this.content = content;
        selected = false;
    }

    public boolean isSelected() {
        return selected;
    }

    /**
     * toggles the state if selected.
     */
    public void toggle() {
        selected = !selected;
    }

    public String getName() {
        return filename;
    }

    public UUID getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return filename;
    }
}
