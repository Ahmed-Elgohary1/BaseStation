package org.example.FileManagement.components;

import org.apache.avro.generic.GenericRecord;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DiskManager {

    private static DiskManager instance;
    public static DiskManager getInstance() {
        if (instance == null) {
            synchronized (DiskManager.class) {
                if (instance == null) {
                    instance = new DiskManager( );
                }
            }
        }
        return instance;
    }

    private String stationDirectory;
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setStationDirectory(String stationDirectory) {
        this.stationDirectory = stationDirectory;
    }

   public void writeWithParquetWriter(List<GenericRecord> records){

   }
   public void writeWithFileWriter(List<GenericRecord> records){



           try {
               FileWriter writer = new FileWriter(fileName);
               for(GenericRecord record:records) {
                   writer.write(record.toString()); // Write the content to the file
               }
               writer.close(); // Remember to close the writer
               System.out.println("Successfully wrote to the file.");
           } catch (IOException e) {
               System.out.println("An error occurred while writing to the file.");
               e.printStackTrace();
           }

   }

}