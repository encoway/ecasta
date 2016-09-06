package com.encoway.ecasta.commons.services;

import java.io.File;

import org.springframework.stereotype.Component;

/**
 * Service to delete a file.
 * 
 * @author azzouz
 */
@Component
public class FileDeletionService {

    /**
     * The method deletes a file.
     * 
     * @param pathFrom Location of the file to be deleted.
     * @param name Name of the file to be deleted.
     * @param fileExtension Extension of the file to be deleted.
     * @return true when deleted
     */
    public boolean deleteFile(String pathFrom, String name, String fileExtension) {
        String path = pathFrom + File.separatorChar + name + fileExtension;
        File file = new File(path);
        return file.delete();
    }

    /**
     * The method deletes all file in a given directory.
     * 
     * @param pathFrom Location of the files to be deleted.
     * @return true if the directory was cleared successfully, false otherwise.
     */
    public boolean clear(String pathFrom) {
        File dir = new File(pathFrom);
        if (dir.isDirectory()) {
            for (File fileEntry : dir.listFiles()) {
                fileEntry.delete();
            }
            return true;
        }
        return false;
    }

}
