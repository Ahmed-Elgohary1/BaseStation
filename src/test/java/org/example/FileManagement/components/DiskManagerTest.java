package org.example.FileManagement.components;

import org.example.FileManagement.FileProcessor;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class DiskManagerTest {

    FileProcessor fileProcessor=FileProcessor.getInstance();



    String directoryName="E:\\project\\Weather-Stations-Monitoring\\BaseStation\\BitCask-Data";

    @Test
    void deleteFile(String fileName) {
        System.out.println(fileName+" "+fileProcessor.diskManager.deleteFile(directoryName+ File.separator+fileName));
    }

    @Test
    void bringAllDirectoryFiles() {
        fileProcessor.enableDiskManager();
        String[] list=fileProcessor.diskManager.bringAllDirectoryFiles(directoryName);

        for(String fileName:list){
            deleteFile(fileName);
        }
    }

    void s(HashMap<String,String > m){

        m.put("hi","Elgo");
    }
    @Test
    void b(){

        HashMap<String,String >m= new HashMap<>();

        s(m);

        System.out.println(m.get("hi"));
    }


}