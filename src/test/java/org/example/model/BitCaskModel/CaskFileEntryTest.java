package org.example.model.BitCaskModel;

import org.example.model.MessageModel.WeatherMessageData;
import org.example.model.MessageModel.WeatherStationMessage;
import org.junit.jupiter.api.Test;

import static org.example.model.BitCaskModel.CaskFileEntry.convertBytesToCaskEntry;

class CaskFileEntryTest {


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
        weatherStationMessage.setBattery_status("high");
        weatherStationMessage.setWeatherMessageData(weatherMessageData);

        return weatherStationMessage;
    }

    @Test
    void convertToBytes() {
        CaskFileEntry caskFileEntry =new CaskFileEntry(1245L,1L,getMessage());


        byte[] value= caskFileEntry.toByteArray();

        System.out.println(convertBytesToCaskEntry(value).getTimestamp());
    }


}