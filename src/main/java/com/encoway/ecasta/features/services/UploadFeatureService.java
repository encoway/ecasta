package com.encoway.ecasta.features.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

/**
 * Importservice for only feature files.
 * 
 * @author azzouz
 */
@Component
public class UploadFeatureService {

    private FileChooser fileChooser;

    /**
     * init-method.
     */
    @PostConstruct
    public void init() {
        fileChooser = new FileChooser();
    }

    /**
     * starts an import dialog.
     * and only feature files could be uploaded
     * 
     * @param stage where the import should be shown.
     * @return the imported file
     */
    public List<File> startImport(Stage stage) {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("FEATURE files (*.feature)", "*.feature");
        fileChooser.getExtensionFilters().add(extFilter);
        List<File> files = fileChooser.showOpenMultipleDialog(stage);
        return files == null ? new ArrayList<File>() : files;
    }
}
