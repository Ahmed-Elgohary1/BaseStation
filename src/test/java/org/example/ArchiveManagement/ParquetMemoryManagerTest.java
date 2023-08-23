package org.example.ArchiveManagement;

import org.apache.avro.generic.GenericRecord;
import org.example.EncoderMangment.AvroEncoder;
import org.example.model.MessageModel.WeatherMessageData;
import org.example.model.MessageModel.WeatherStationMessage;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ParquetMemoryManagerTest {

    @Test
    void messageArchiving() throws IOException {



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
        List<GenericRecord> stationInmemoryBatch=new ArrayList<>();
        stationInmemoryBatch.add(record);
        ParquetMemoryManager parquetMemoryManager=ParquetMemoryManager.getInstance(0);

        parquetMemoryManager.messageArchiving(weatherStationMessage);


    }
}