package org.example.ArchiveManagement;

import org.apache.avro.generic.GenericRecord;
import org.example.EncoderMangment.AvroEncoder;
import org.example.FileManagement.FileProcessor;
import org.example.model.MessageModel.WeatherStationMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

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
        parquetPath="E:\\project\\Weather-Stations-Monitoring\\BaseStation\\ParquetArch\\";
        fileProcessor.enableNameManager();
        fileProcessor.enableDiskManager();

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


    public void messageArchiving(WeatherStationMessage weatherStationMessage) throws IOException {


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
            String fileName=fileProcessor.nameManager.generateUniquePathName( stationDirectory,"//",".parquet");


            fileProcessor.diskManager.writeWithFileWriter(stationInmemoryBatch,fileName);

            stationInmemoryBatch.clear();


        }


    }




}
