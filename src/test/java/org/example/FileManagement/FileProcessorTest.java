package org.example.FileManagement;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.ParquetFileWriter.Mode;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.example.EncoderMangment.AvroEncoder;
import org.example.model.MessageModel.WeatherMessageData;
import org.example.model.MessageModel.WeatherStationMessage;
import org.junit.jupiter.api.Test;

class FileProcessorTest {



@Test
    public void writeToParquet() {


    String avroSchemaString = "{\"type\":\"record\",\"name\":\"WeatherData\",\"fields\":[{\"name\":\"station_id\",\"type\":\"long\"},{\"name\":\"s_no\",\"type\":\"long\"},{\"name\":\"battery_status\",\"type\":\"string\"},{\"name\":\"status_timestamp\",\"type\":\"long\"},{\"name\":\"weather\",\"type\":{\"type\":\"record\",\"name\":\"Weather\",\"fields\":[{\"name\":\"humidity\",\"type\":\"int\"},{\"name\":\"temperature\",\"type\":\"int\"},{\"name\":\"wind_speed\",\"type\":\"int\"}]}}]}";
    Schema avroSchema = new Schema.Parser().parse(avroSchemaString);
    // Path to Parquet file in HDFS

    String now = "base/Archive" + "/" + "k" + "/";
    System.out.println("writing in: " + now);
    ParquetWriter<GenericRecord> writer = null;
    // Creating ParquetWriter using builder
    try {
        Files.createDirectories(Paths.get(now));
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");
        Path path = new Path("file:///" + now + "/" + System.currentTimeMillis() / 1000 + ".parquet");
        writer = AvroParquetWriter.<GenericRecord>builder(path)
                .withSchema(avroSchema)
                .withWriteMode(Mode.OVERWRITE)
                .withConf(conf)
                .build();



    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

@Test
 void writeRecord(){

    /*
    {"station_id":1,"s_no":5,"battery_status":"Low","status_timestamp":null,
    "weatherMessageData":{"humidity":81,"temperature":73,"wind_speed":4}}
     */

    WeatherStationMessage weatherStationMessage=new WeatherStationMessage();
    WeatherMessageData weatherMessageData=new WeatherMessageData();
    weatherMessageData.setHumidity(81);
    weatherMessageData.setTemperature(73);
    weatherMessageData.setWind_speed(4);

    weatherStationMessage.setStation_id(1L);
    weatherStationMessage.setStatus_timestamp(165644546L);
    weatherStationMessage.setS_no(2L);
    weatherStationMessage.setWeatherMessageData(weatherMessageData);

    AvroEncoder avroEncoder=new AvroEncoder();

    GenericRecord record=avroEncoder.createEncodedRecord(weatherStationMessage);
    List<GenericRecord>stationInmemoryBatch=new ArrayList<>();
    stationInmemoryBatch.add(record);

    FileProcessor fileProcessor=FileProcessor.getInstance();
    fileProcessor.enableNameManager();
    fileProcessor.enableDiskManager();


    String stationDirectory=fileProcessor.nameManager.appendDirectory("E:\\project\\Weather-Stations-Monitoring\\BaseStation\\" ,"Cask");
    String fileName=fileProcessor.nameManager.generateUniquePathName( stationDirectory,"//",".cask");

    fileProcessor.diskManager.writeWithFileWriter(stationInmemoryBatch,fileName);

    System.out.println(record);

}

}


