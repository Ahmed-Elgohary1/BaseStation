package org.example.model.MessageModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherStationMessageTest {



    WeatherMessageData getDataMessage(int humd,int temp,int wind_speed){
        WeatherMessageData weatherMessageData=new WeatherMessageData();
        weatherMessageData.setHumidity(humd);
        weatherMessageData.setTemperature(temp);
        weatherMessageData.setWind_speed(wind_speed);

        return weatherMessageData;
    }


    WeatherStationMessage getMessage(){
        WeatherStationMessage weatherStationMessage=new WeatherStationMessage();

        weatherStationMessage.setWeatherMessageData(getDataMessage(12,55,46));
        weatherStationMessage.setStation_id(1L);
        weatherStationMessage.setBattery_status("Low");
        weatherStationMessage.setS_no(25L);
        weatherStationMessage.setStatus_timestamp(454654564L);


        return weatherStationMessage;
    }
    @Test
    byte[] toByteArray() {
        byte[] value= getMessage().toByteArray();

     return value;
    }

    @Test
    void convertByteToWeatherStatonMessage() {
        byte[] value=toByteArray();
        WeatherStationMessage weatherStationMessage=WeatherStationMessage.convertByteToWeatherStationMessage(value);

        System.out.println(weatherStationMessage.toString());

    }
}