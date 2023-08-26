package org.example.FileManagement.components;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.hadoop.conf.Configuration;
import org.example.model.BitCaskModel.DataDiskIndex;
import org.example.model.MessageModel.WeatherStationMessage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
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

    public byte[] readFromFile(DataDiskIndex dataDiskIndex){
        String fileName= dataDiskIndex.getFileName();
        Long recordPointer= dataDiskIndex.getOffset();
        int size= dataDiskIndex.getSize();


        byte[] fileEntry=null;

        try {
            RandomAccessFile accessFile=new RandomAccessFile(fileName,"r");
            accessFile.seek(recordPointer);
            fileEntry=new byte[size];
            accessFile.read(fileEntry);
            accessFile.close();

        }catch (IOException e){
            e.printStackTrace();
        }

      return fileEntry;
    }

   public Long writeInFile(RandomAccessFile activeFile, byte[] fileEntry){
       Long recordPointer=0L;
        try {
            recordPointer = activeFile.getFilePointer();
            activeFile.write(fileEntry);
        }
        catch (IOException e){
            e.printStackTrace();
        }

    return recordPointer;
   }

   public Boolean deleteFile(String filePath){

        boolean noMoreExist=true;
       File file=new File(filePath);
       if(file.exists())
           noMoreExist=file.delete();

       return noMoreExist;
   }

   public String[] bringAllDirectoryFiles(String directoryPath){
        String[]filesName;

        File directory=new File(directoryPath);

        filesName=directory.list();

        return filesName;
   }


   public void writeWithParquetWriter(List<GenericRecord> records,String fileName) throws IOException {

       Schema schema= WeatherStationMessage.getWeatherStationMessageAvroSchema();
       Configuration conf = new Configuration();
       FileSystem fileSystem = FileSystem.get(conf);
       ParquetWriter<GenericRecord> writer ;
       Path file=new Path(fileName);
       try {
           writer = AvroParquetWriter
                   .<GenericRecord>builder(file)
                   .withSchema(schema)
                   .withDataModel(GenericData.get())
                   .withConf(conf)
                   .build();
       } catch (IOException e) {
           System.out.println("I haven't configured yet");
           throw new RuntimeException(e);
       }

       try {


           for (GenericRecord record : records) {
               System.out.println(record.toString());
               writer.write(record);
           }
           writer.close();

       }
       catch (IOException e){
           System.out.println("An error occurred while writing to the file.");
           e.printStackTrace();
       }
       System.out.println("Wrote to parquet file!");


   }
   public void writeWithFileWriter(List<GenericRecord> records,String fileName){



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
