package org.example.StorageManagment;

import org.example.FileManagement.FileProcessor;
import org.example.model.BitCaskModel.DataDiskIndex;
import org.example.model.MessageModel.WeatherStationMessage;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashMap;

public class BitCaskManager {

    private final String bitcaskDirectory;
    private File fileID;
    private final long FILE_THRESHOLD = (long) 1 * 1024;  // 1KB
    private RandomAccessFile activeFile;
    private int uncompactedFiles;
    HashMap<String, DataDiskIndex>keyDir;

   public BitCaskManager(){
       FileProcessor fileProcessor=FileProcessor.getInstance();


       this.bitcaskDirectory=fileProcessor.nameManager
               .appendDirectory("E:\\project\\Weather-Stations-Monitoring\\BaseStation\\" ,"BitCask-Data");
       this.uncompactedFiles=0;

   }

   public void put(WeatherStationMessage weatherStationMessage){
       Long station_id= weatherStationMessage.getStation_id();

   }
   public void addRecord(){

   }

}
