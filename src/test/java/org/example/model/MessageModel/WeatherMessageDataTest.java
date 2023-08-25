package org.example.model.MessageModel;

import org.junit.jupiter.api.Test;

import static org.example.model.MessageModel.WeatherMessageData.convertByteToWeatherDataMessage;

class WeatherMessageDataTest {


    WeatherMessageData getMessage(int humd,int temp,int wind_speed){
        WeatherMessageData weatherMessageData=new WeatherMessageData();
        weatherMessageData.setHumidity(humd);
        weatherMessageData.setTemperature(temp);
        weatherMessageData.setWind_speed(wind_speed);

        return weatherMessageData;
    }


    byte[] toByteArray() {
        WeatherMessageData weatherMessageData=getMessage(12,51,99);
        byte[]value=weatherMessageData.toByteArray();

        return value;
    }

    @Test
    void convertByteToWeatherMessageDat() {
        WeatherMessageData weatherMessageData=convertByteToWeatherDataMessage(toByteArray());

        System.out.println(weatherMessageData.toString());

    }
}