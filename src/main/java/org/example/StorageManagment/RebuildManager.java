package org.example.StorageManagment;

import org.example.FileManagement.FileProcessor;
import org.example.model.BitCaskModel.DataDiskIndex;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.example.model.BitCaskModel.DataDiskIndex.convertBytesToDataDiskIndex;

public class RebuildManager {

    FileProcessor fileProcessor=FileProcessor.getInstance();
    private RandomAccessFile activeFile;
    private final String bitcaskDirectory;

    private List<String>hintFiles;


    HashMap<Long, DataDiskIndex>keyDir;

   public RebuildManager() {
       this.bitcaskDirectory = fileProcessor.nameManager
               .appendDirectory("E:\\project\\Weather-Stations-Monitoring\\BaseStation\\", "BitCask-Data");

       fileProcessor.enableNameManager();
       fileProcessor.enableDiskManager();

       hintFiles = new ArrayList<>();

       String[] directoryFile = fileProcessor.diskManager.bringAllDirectoryFiles(bitcaskDirectory);
       for (String fileName : directoryFile) {
           String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
           if (extension.equals("hint")) {
               hintFiles.add(fileName);
           }
       }


   }


   public void rebuild(HashMap<Long,DataDiskIndex> keyDir){
       for(String fileName:hintFiles){
           try {
               if (activeFile.getFilePointer() >= activeFile.length()) return ;

                Long key=activeFile.readLong();
               int dataDiskIndexByteSize= activeFile.readInt();
               byte[] dataDiskIndexByte=new byte[dataDiskIndexByteSize];
               activeFile.read(dataDiskIndexByte);

               DataDiskIndex dataDiskIndex =convertBytesToDataDiskIndex(dataDiskIndexByte);

                keyDir.put(key,dataDiskIndex);

           }
           catch (IOException e){
               e.printStackTrace();
           }
       }

   }

}
