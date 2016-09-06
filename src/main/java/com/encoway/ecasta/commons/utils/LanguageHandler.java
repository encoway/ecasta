package com.encoway.ecasta.commons.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.stereotype.Service;

/**
 * Handles languages to support multilingualism.
 * in GUI language.
 */
@Service
public class LanguageHandler {

    private ResourceBundle defaultBundle;

    public LanguageHandler() {
        Locale locale = new Locale("en", "EN");
        defaultBundle = ResourceBundle.getBundle("bundles/testsuite", locale);
    }

    public ResourceBundle getDefaultResourceBundle() {
        return defaultBundle;
    }

    /**
     * returns the resourcebundle for a specific language.
     * 
     * @param localeString the key of the specific language
     * @return {@link ResourceBundle}
     */
    public ResourceBundle getResourceBundle(String localeString) {
        Locale locale = new Locale(localeString);
        return ResourceBundle.getBundle("bundles/testsuite", locale);
    }

    /**
     * returns the text for a specific key.
     * 
     * @param key the specific key
     * @return a string
     */
    public String getMessage(String key) {
        return defaultBundle.getString(key);
    }

}
