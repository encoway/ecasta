package com.encoway.ecasta.commons.utils;

import com.encoway.ecasta.commons.dialogs.WebViewImpl;

import java.net.MalformedURLException;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.stereotype.Component;

/**
 * Factory for a web view for the result.
 * 
 * @author azzouz
 */
@Component
public class StageFactory {

    /**
     * Creates a {@link Stage} to show the results.
     * 
     * @param title The title of the window.
     * @return The created {@link Stage}.
     * @throws MalformedURLException Is thrown when the results are not found.
     */
    public Stage createStage(String title) throws MalformedURLException {
        Stage stage = new Stage();
        stage.setTitle(title);
        Scene scene = new Scene(new WebViewImpl(), Color.web("#666970"));
        stage.setScene(scene);
        stage.getIcons().add(new Image("icons/app-icon.png"));
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent arg0) {
                stage.close();
            }
        });
        return stage;
    }
}
