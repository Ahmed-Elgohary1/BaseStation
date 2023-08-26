package org.example.StorageManagment;

import org.example.FileManagement.FileProcessor;
import org.example.model.BitCaskModel.CaskFileEntry;
import org.example.model.BitCaskModel.DataDiskIndex;
import org.example.model.BitCaskModel.HintFileEntry;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

import static org.example.model.BitCaskModel.CaskFileEntry.convertBytesToCaskEntry;

public class FileCompactor {

    FileProcessor fileProcessor=FileProcessor.getInstance();

    private final String bitcaskDirectory;


    private File compactFile;
    private File hintFile;
    private RandomAccessFile activeFile;

    HashMap<Long, DataDiskIndex> keyDir;


    public FileCompactor(HashMap<Long, DataDiskIndex>keyDir){

        this.bitcaskDirectory =fileProcessor.nameManager
                .appendDirectory("E:\\project\\Weather-Stations-Monitoring\\BaseStation\\" ,"BitCask-Data");
        fileProcessor.enableDiskManager();
        fileProcessor.enableNameManager();


        String compactFileName=fileProcessor.nameManager.generateUniquePathName(this.bitcaskDirectory,"//",".cask");
        String hintFileName=fileProcessor.nameManager.generateUniquePathName(this.bitcaskDirectory,"//",".hint");


        this.keyDir=keyDir;
        this.compactFile=new File(compactFileName);
        this.hintFile =new File(hintFileName);

    }



    public void merge() {
        String [] oldFiles=fileProcessor.diskManager.bringAllDirectoryFiles(bitcaskDirectory);

        try{
            activeFile=new RandomAccessFile(compactFile,"rw");

            for(Map.Entry<Long,DataDiskIndex>set: keyDir.entrySet()){

                DataDiskIndex oldDataDiskIndex=set.getValue();
              set.setValue(transfer(oldDataDiskIndex));


            }

        }
        catch (IOException e){
            e.printStackTrace();
        }

        try{
            System.out.println("HINT");
            activeFile=new RandomAccessFile(hintFile,"rw");
            for(Map.Entry<Long,DataDiskIndex>set: keyDir.entrySet()) {
                writeHintFile(set.getKey(), set.getValue());
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }


        for(String fileName:oldFiles){
            fileProcessor.diskManager.deleteFile(bitcaskDirectory+"//"+fileName);
        }

    }


    private DataDiskIndex transfer(DataDiskIndex oldDataDiskIndex){


        byte[] fileEntry=fileProcessor.diskManager.readFromFile(oldDataDiskIndex);

        Long offset=fileProcessor.diskManager.writeInFile(activeFile,fileEntry);

        DataDiskIndex newDataDiskIndex=new DataDiskIndex(bitcaskDirectory+"//"+compactFile.getName(),offset,fileEntry.length);
     return newDataDiskIndex;
    }

    private void writeHintFile(Long key,DataDiskIndex index){

        HintFileEntry hintFileEntry=new HintFileEntry(key, index);

        byte[]fileEntry=hintFileEntry.toByteArray();

        fileProcessor.diskManager.writeInFile(activeFile,fileEntry);


    }



}
