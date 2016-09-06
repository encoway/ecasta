package com.encoway.ecasta;

import com.encoway.ecasta.commons.controller.MainViewController;
import com.encoway.ecasta.commons.dialogs.ErrorDialog;
import com.encoway.ecasta.commons.events.InitializationEvent;
import com.encoway.ecasta.commons.utils.ContextHolder;
import com.encoway.ecasta.commons.utils.LanguageHandler;
import com.encoway.ecasta.commons.utils.ViewConstants;

import com.google.common.eventbus.EventBus;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MainApp.
 * starts the application
 * 
 * @author azzouz
 */
public class MainApp extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainApp.class);

    @Override
    public void start(Stage stage) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(MainApp::showError);
        try {
            stage = ContextHolder.getContext().getBean(Stage.class);

            MainViewController controller = (MainViewController) SpringFxmlLoader.load("/fxml/mainView.fxml");
            EventBus eventBus = ContextHolder.getContext().getBean(EventBus.class);
            LanguageHandler languageHandler = ContextHolder.getContext().getBean(LanguageHandler.class);
            StackPane root = (StackPane) controller.getView();

            Scene scene = new Scene(root);

            stage.setTitle(languageHandler.getMessage(ViewConstants.TITLE));
            stage.getIcons().add(new Image("icons/app-icon.png"));
            stage.setScene(scene);
            stage.show();

            // post an event to load all stored data
            eventBus.post(new InitializationEvent());
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent arg0) {
                    LOGGER.info("CLOSE APPLICATION");
                    Platform.exit();
                }
            });

        } catch (Exception e) {
            LOGGER.error(e.toString());
            LOGGER.info("CLOSE APPLICATION");
            Platform.exit();
        }

    }

    /**
     * shows an exception.
     * 
     * @param t the Thread in which the exception occurred
     * @param e the exception
     */
    public static void showError(Thread t, Throwable e) {
        new ErrorDialog(e);
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args
     * the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
