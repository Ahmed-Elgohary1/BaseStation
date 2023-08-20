package org.example.FileManagement;

import org.example.FileManagement.components.DiskManager;
import org.example.FileManagement.components.NameManager;

public class FileProcessor {

    public NameManager nameManager;
    public DiskManager diskManager;


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
    public void enableDiskManager(){
        diskManager=DiskManager.getInstance();
    }



}
