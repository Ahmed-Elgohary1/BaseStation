package org.example.FileManagement;

import org.example.FileManagement.components.NameManager;

public class FileProcessor {

    public NameManager nameManager;


    private static FileProcessor instance;
    private FileProcessor() { }

    public static FileProcessor getInstance() {
        if (instance == null) {
            synchronized (FileProcessor.class) {
                if (instance == null) {
                    instance = new FileProcessor();
                }
            }
        }
        return instance;
    }

    public void enableNameManager(){
        nameManager=NameManager.getInstance();
    }



}
