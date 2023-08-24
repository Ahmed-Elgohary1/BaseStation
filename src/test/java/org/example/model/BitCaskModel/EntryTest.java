package org.example.model.BitCaskModel;

import org.example.model.MessageModel.WeatherMessageData;
import org.example.model.MessageModel.WeatherStationMessage;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class EntryTest {


    WeatherStationMessage getMessage(){
        WeatherStationMessage weatherStationMessage=new WeatherStationMessage();
        WeatherMessageData weatherMessageData=new WeatherMessageData();
        weatherMessageData.setHumidity(81);
        weatherMessageData.setTemperature(73);
        weatherMessageData.setWind_speed(4);

        weatherStationMessage.setStation_id(1L);
        weatherStationMessage.setStatus_timestamp(165644546L);
        weatherStationMessage.setS_no(2L);
        weatherStationMessage.setStatus_timestamp(12566785L);
        weatherStationMessage.setWeatherMessageData(weatherMessageData);

        return weatherStationMessage;
    }

    @Test
    void convertToBytes() {
        Entry entry=new Entry(1245L,1L,getMessage());


        byte[] value=entry.convertToBytes();
        System.out.println(value.length);
    }


}