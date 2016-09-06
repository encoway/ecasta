package com.encoway.ecasta;

import com.encoway.ecasta.commons.controller.AbstractController;
import com.encoway.ecasta.commons.utils.ContextHolder;
import com.encoway.ecasta.commons.utils.LanguageHandler;

import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.util.Callback;
import org.apache.log4j.Logger;

/**
 * Loads FXML and replaces controller by beans from spring context.
 */
public class SpringFxmlLoader {

    private static final Logger LOGGER = Logger.getLogger(SpringFxmlLoader.class);

    /**
     * Loads a fxml-file and creates the view.
     * 
     * @param url location of the fxml file
     * @param bundles
     * @return the controller of created view
     */
    public static AbstractController load(String url) {
        try (InputStream fxmlStream = SpringFxmlLoader.class.getResourceAsStream(url)) {
            LanguageHandler languageHandler = ContextHolder.getContext().getBean(LanguageHandler.class);
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(languageHandler.getDefaultResourceBundle());
            loader.setControllerFactory(new Callback<Class<?>, Object>() {

                @Override
                public Object call(Class<?> aClass) {
                    return ContextHolder.getContext().getBean(aClass);
                }
            });

            Node view = (Node) loader.load(fxmlStream);
            AbstractController controller = loader.getController();
            controller.setView(view);

            return controller;
        } catch (IOException e) {
            LOGGER.error("Can't load resource", e);
            throw new RuntimeException(e);
        }
    }
}