package org.example.StorageManagment;

import org.example.FileManagement.FileProcessor;
import org.example.model.MessageModel.WeatherMessageData;
import org.example.model.MessageModel.WeatherStationMessage;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;

class BitCaskManagerTest {



    BitCaskManager bitCaskManager=new BitCaskManager();

    @Test
    void put(Long i) throws IOException {
        WeatherStationMessage weatherStationMessage=getMessage(i);
          //  BitCaskManager bitCaskManager=new BitCaskManager();
        bitCaskManager.put(weatherStationMessage);
    }

    @Test
    void addRecord() {
    }


    WeatherStationMessage getMessage(Long i){
        WeatherStationMessage weatherStationMessage=new WeatherStationMessage();
        WeatherMessageData weatherMessageData=new WeatherMessageData();
        weatherMessageData.setHumidity(88888);
        weatherMessageData.setTemperature(7777777);
        weatherMessageData.setWind_speed(44444444);

        weatherStationMessage.setStation_id(i);
        weatherStationMessage.setStatus_timestamp(5555555555555555555L);
        weatherStationMessage.setS_no(1111111111111111111L);
        weatherStationMessage.setWeatherMessageData(weatherMessageData);

        return weatherStationMessage;
    }

    @Test
    void read() throws IOException {
        FileProcessor fileProcessor=FileProcessor.getInstance();

        put(1L);

        byte[] byteArray=bitCaskManager.read(1L);

        for(int i=0;i<byteArray.length-8;i++) {
            int offset = i; // start from index 0
            long value = ByteBuffer.wrap(byteArray, offset, Long.BYTES)
                    .getLong();
            System.out.println(value+" + "+i);
        }

        /*
        * hard coded
        * station id is at the offset 249
        * time stamp at 263
        *
        * */

    }

    @Test
    void convert(){

        long value = 1L;

// Create a ByteBuffer and allocate space for 8 bytes (length of a long)
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);

// Put the long value into the buffer
        buffer.putLong(value);

// Get the byte array from the buffer
        byte[] byteArray = buffer.array();

        Integer valu = ByteBuffer.wrap(byteArray, 4, Integer.BYTES)
                .getInt();
        System.out.println(byteArray);

        System.out.println(valu);
    }
}