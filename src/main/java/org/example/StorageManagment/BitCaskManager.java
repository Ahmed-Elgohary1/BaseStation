package org.example.StorageManagment;

import org.example.FileManagement.FileProcessor;
import org.example.model.BitCaskModel.DataDiskIndex;
import org.example.model.BitCaskModel.Entry;
import org.example.model.MessageModel.WeatherStationMessage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

public class BitCaskManager {

    FileProcessor fileProcessor=FileProcessor.getInstance();

    private final String bitcaskDirectory;
    private File fileID;
    private final long FILE_THRESHOLD = (long) 10 * 1024;  // 10KB
    private RandomAccessFile activeFile;
    private int uncompactedFiles;
    HashMap<Long, DataDiskIndex>keyDir;

   public BitCaskManager(){


       this.fileProcessor.enableDiskManager();
       this.fileProcessor.enableNameManager();

       this.bitcaskDirectory=fileProcessor.nameManager
               .appendDirectory("E:\\project\\Weather-Stations-Monitoring\\BaseStation\\" ,"BitCask-Data");

       createNewFile();

       this.uncompactedFiles=1;

       keyDir=new HashMap<>();

   }



   public void put(WeatherStationMessage weatherStationMessage) throws IOException {
       Long station_id= weatherStationMessage.getStation_id();
       Long status_timestamp= weatherStationMessage.getStatus_timestamp();

       Entry entry =new Entry(status_timestamp,station_id,weatherStationMessage);
       byte[] fileEntry=entry.toByteArray();
       try {
             if(!currentFileCanAppendEntry(fileEntry)){
                  activeFile.close();
                createNewFile();
              }
       }
       catch (IOException e){
           e.printStackTrace();
       }

       Long offset=fileProcessor.diskManager.writeInFile(activeFile,fileEntry);

       DataDiskIndex dataDiskIndex=new DataDiskIndex(bitcaskDirectory+"//"+fileID.getName(),offset,fileEntry.length);

       keyDir.put(station_id,dataDiskIndex);

   }

   public byte[] read(Long station_id){

       byte[] data;

       if(!keyDir.containsKey(station_id)){
           throw new RuntimeException("we don't have that station");
       }
       DataDiskIndex dataDiskIndex=keyDir.get(station_id);

       System.out.println(dataDiskIndex.getFileName()+"   "+dataDiskIndex.getOffset()+"  "+dataDiskIndex.getSize());

       data=fileProcessor.diskManager.readFromFile(dataDiskIndex);

       return data;
   }



    private void createNewFile(){
        String filename=fileProcessor.nameManager.generateUniquePathName(this.bitcaskDirectory,"//",".cask");

        try{
            fileID=new File(filename);
            activeFile = new RandomAccessFile(fileID, "rw");
            System.out.println(fileID.getName());
        }
        catch (IOException e){
            e.printStackTrace();
        }
        uncompactedFiles++;
    }

   private Boolean currentFileCanAppendEntry(byte[]fileEntry) throws IOException {
       if(activeFile.length()+fileEntry.length>FILE_THRESHOLD)
            return false;

  return true;
   }


}
