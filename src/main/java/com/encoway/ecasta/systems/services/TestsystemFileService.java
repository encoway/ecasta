package com.encoway.ecasta.systems.services;

import com.encoway.ecasta.commons.services.FileDeletionService;
import com.encoway.ecasta.commons.utils.PathConstants;
import com.encoway.ecasta.systems.Testsystem;
import com.encoway.ecasta.systems.utils.JsonTransformer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Handles the storage of testsystems.
 * loads, saves and updates testsystems.
 * 
 * @author azzouz
 */
@Component
public class TestsystemFileService {

    @Autowired
    private JsonTransformer jsonTransformer;

    @Autowired
    private FileDeletionService deletionService;

    /**
     * loads all stored testsystems.
     * 
     * @return a list of testsystem-object
     */
    public List<Testsystem> loadTestsystems() {
        File dir = new File(PathConstants.TESTSYSTEM_PATH);
        List<Testsystem> list = new ArrayList<Testsystem>();
        if (dir.isDirectory()) {
            for (File fileEntry : dir.listFiles()) {
                if (!fileEntry.isDirectory()) {
                    Testsystem ts = jsonTransformer.getTestsystemFromJson(fileEntry.getAbsolutePath());
                    list.add(ts);
                }
            }
        }
        return list;
    }

    /**
     * saves the given testsystem as JSON.
     * 
     * @param ts the given testsystem
     */
    public void saveAsJson(Testsystem ts) {
        String pathTo = PathConstants.TESTSYSTEM_PATH;
        if (checkDir(pathTo)) {
            jsonTransformer.transferToJson(ts, pathTo + "\\" + ts.getName() + ".json");
        }
    }

    private static boolean checkDir(String pathTo) {
        File file = new File(pathTo);
        if (!file.isDirectory()) {
            return file.mkdirs();
        }
        return file.isDirectory();

    }

    /**
     * updates an already stored testsystem.
     * 
     * @param oldTestsystem testsystem which will be replaced
     * @param testsystem the new testsystem
     */
    public void update(Testsystem oldTestsystem, Testsystem testsystem) {
        if (deletionService.deleteFile(PathConstants.TESTSYSTEM_PATH, oldTestsystem.getName(), Testsystem.FILE_EXTENSION)) {
            saveAsJson(testsystem);
        }
    }
}
