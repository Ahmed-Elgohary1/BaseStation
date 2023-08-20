package org.example.ArchiveManagement;

import org.apache.avro.generic.GenericRecord;
import org.example.EncoderMangment.AvroEncoder;
import org.example.FileManagement.FileProcessor;
import org.example.model.WeatherStationMessage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import static org.apache.commons.compress.harmony.pack200.PackingUtils.log;

public class ParquetMemoryManager {
    private final HashMap<Long, List<GenericRecord>> batchManager;
    private final int BatchSize;
    private final AvroEncoder avroEncoder;

    private FileProcessor fileProcessor=FileProcessor.getInstance();

    private final String parquetPath;


    private static final Logger log = Logger.getLogger(ParquetMemoryManager.class.getName());



    private static ParquetMemoryManager instance;

    private ParquetMemoryManager(int batchSize) {
        BatchSize=batchSize;
        batchManager=new HashMap<>();
        avroEncoder=new AvroEncoder();
        parquetPath="E:\\project\\BaseStation\\ParquetArch\\";
        fileProcessor.enableNameManager();

    }

    public static ParquetMemoryManager getInstance(int batchSize) {
        if (instance == null) {
            synchronized (ParquetMemoryManager.class) {
                if (instance == null) {
                    instance = new ParquetMemoryManager( batchSize);
                }
            }
        }
        return instance;
    }


    public void messageArchiving(WeatherStationMessage weatherStationMessage){


        Long stationId= weatherStationMessage.getStation_id();
        GenericRecord record=avroEncoder.createEncodedRecord(weatherStationMessage);


        List<GenericRecord> stationInmemoryBatch;

        if(batchManager.containsKey(stationId)){
            stationInmemoryBatch=batchManager.get(stationId);
        }
        else{
            stationInmemoryBatch= new ArrayList<>();
            batchManager.put(stationId,stationInmemoryBatch);
        }

       stationInmemoryBatch.add(record);
        batchManager.put(stationId,stationInmemoryBatch);


        log.info("Current Batch and its Size "+stationId.toString()+"  "+stationInmemoryBatch.size());


        if(stationInmemoryBatch.size()> BatchSize){
            String stationDirectory=fileProcessor.nameManager.appendDirectory(parquetPath ,stationId.toString());
            String fileName=fileProcessor.nameManager.generateUniquePathName( stationDirectory,"//",".txt");




            try {
                FileWriter writer = new FileWriter(fileName);
                writer.write("Hello, World!"); // Write the content to the file
                writer.close(); // Remember to close the writer
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }


        }


    }




}
