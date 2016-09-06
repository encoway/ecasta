package com.encoway.ecasta.systems.utils;

import com.encoway.ecasta.commons.services.FileDeletionService;
import com.encoway.ecasta.commons.utils.PathConstants;
import com.encoway.ecasta.features.services.FeatureFileService;
import com.encoway.ecasta.systems.Testsystem;
import com.encoway.ecasta.systems.services.TestsystemFileService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Handles all Testsystems.
 * and it holds them
 * 
 * @author Azzouz
 */
@Component
public class TestsystemHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestsystemHandler.class);

    @Autowired
    private TestsystemFileService fileService;

    @Autowired
    private FileDeletionService deletionService;

    private Map<UUID, Testsystem> tsMap = new HashMap<>();
    private List<Testsystem> testsystemList = new ArrayList<>();

    /**
     * init-method.
     */
    @PostConstruct
    public void init() {
        testsystemList = fileService.loadTestsystems();
        testsystemList.forEach(element -> tsMap.put(element.getId(), element));
    }

    public List<Testsystem> getTestsystems() {
        return testsystemList;
    }

    /**
     * adds a given Testsystem to List.
     * 
     * @param ts the testsystem to add
     */
    public void addTestsystem(Testsystem ts) {
        tsMap.put(ts.getId(), ts);
        testsystemList.add(ts);
        fileService.saveAsJson(ts);
        LOGGER.info(ts.getName() + "added");
    }

    /**
     * Puts the selected Features of the testsystem into the Folder.
     * where Cucumber-Runner looks for
     * 
     * @param testsystem the testsystem which containts the features.
     */
    public void prepareFeaturesToTest(Testsystem testsystem) {
        deletionService.clear(PathConstants.FEATURE_FILE_PATH);
        testsystem.getFeatures().forEach(element -> {
            try {
                if (element.isSelected()) {
                    FeatureFileService.saveFile(PathConstants.FEATURE_FILE_PATH, element.getName(), element.getContent());
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        });

    }

    /**
     * Deletes a Testsystem from by the given ID.
     * 
     * @param id of the Testsystem
     */
    public void deleteTestsystem(UUID id) {
        Testsystem toDelete = tsMap.get(id);
        if (deletionService.deleteFile(PathConstants.TESTSYSTEM_PATH, toDelete.getName(), Testsystem.FILE_EXTENSION)) {
            toDelete.getFeatures().forEach(element -> deletionService.deleteFile(PathConstants.FEATURE_FILE_PATH, element.getName(), ""));
            LOGGER.info(toDelete.getName() + "deleted");
            testsystemList.remove(toDelete);
        }
    }

    /**
     * returns a Testsystem by the given ID.
     * 
     * @param id of the Testsystem
     * @return Testsystem the testsystem
     */
    public Testsystem getTestsystemById(UUID id) {
        return tsMap.get(id);
    }

    /**
     * updates a Testsystem, when it's changed.
     * 
     * @param testsystem which will be updated
     */
    public void updateTestsystem(Testsystem testsystem) {
        Testsystem oldTestsystem = tsMap.get(testsystem.getId());
        fileService.update(oldTestsystem, testsystem);
        tsMap.put(testsystem.getId(), testsystem);
        LOGGER.info(testsystem.getName() + "updated");
    }
}
