package org.example.FileManagement.components;

import org.example.FileManagement.FileProcessor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class NameManager {




    private static NameManager instance;
    private NameManager() { }

    public static NameManager getInstance() {
        if (instance == null) {
            synchronized (NameManager.class) {
                if (instance == null) {
                    instance = new NameManager();
                }
            }
        }
        return instance;
    }


    public String appendDirectory(String absolutePath,String subDir ){

        String directoryPath=absolutePath+subDir;

        File directory = new File(directoryPath);

        // Check if the directory exists
        if (!directory.exists()) {
            // Create the directory if it doesn't exist
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + directory.getAbsolutePath());
            } else {
                System.out.println("Failed to create directory: " + directory.getAbsolutePath());
            }
        }

        return directoryPath;
    }


    public String generateUniquePathName(String intermediatePath,String fileName ,String fileFormat){
        String pathName= intermediatePath;
        pathName+=fileName;
        // Get the current timestamp in milliseconds
        long timestamp = System.currentTimeMillis();
        // Format the timestamp as a string with the format "yyyyMMdd-HHmm ssSSS"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm-ssSSS");
        String timestampStr = dateFormat.format(new Date(timestamp));
        pathName+=  timestampStr;
        pathName+=fileFormat;
        return pathName;
    }

}
