package org.example.model.BitCaskModel;

import org.example.model.MessageModel.WeatherStationMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Entry implements Serializable {
    private Long timestamp;
    private Long stationId;
    private WeatherStationMessage weatherMessage;

    public Entry(Long timestamp, Long stationId, WeatherStationMessage weatherStationMessage){
        this.stationId=stationId;
        this.timestamp=timestamp;
        this.weatherMessage=weatherStationMessage;
    }

    public  byte[] convertToBytes() {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream= null;
        byte[]ans=null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            ans=byteArrayOutputStream.toByteArray();
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }finally {
            try{
                if (objectOutputStream!=null){
                    objectOutputStream.close();
                    byteArrayOutputStream.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        return ans;
    }

    public String es(){
        return this.toString();
    }

    public Long getStationId() {
        return stationId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public WeatherStationMessage getWeatherMessage() {
        return weatherMessage;
    }
}
