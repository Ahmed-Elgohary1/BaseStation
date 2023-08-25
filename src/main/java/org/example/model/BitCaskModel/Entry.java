package org.example.model.BitCaskModel;

import org.example.model.MessageModel.WeatherMessageData;
import org.example.model.MessageModel.WeatherStationMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

import static org.example.model.MessageModel.WeatherMessageData.convertByteToWeatherDataMessage;
import static org.example.model.MessageModel.WeatherStationMessage.convertByteToWeatherStationMessage;

public class Entry implements Serializable {
    private Long timestamp;
    private Long stationId;
    private WeatherStationMessage weatherMessage;

    public Entry(Long timestamp, Long stationId, WeatherStationMessage weatherStationMessage){
        this.stationId=stationId;
        this.timestamp=timestamp;
        this.weatherMessage=weatherStationMessage;
    }
    public Entry(){}

    public  byte[] toByteArray() {

        byte[] weatherStationMessageBytes=weatherMessage.toByteArray();

        int totalSize=8+8+4+weatherStationMessageBytes.length;

        ByteBuffer buffer = ByteBuffer.allocate(totalSize);  // Assuming Long size = 8 bytes, Integer size = 4 bytes

        // Add timestamp to the byte array
        buffer.putLong(timestamp);

        // Add stationId to the byte array
        buffer.putLong(stationId);

        buffer.putInt(weatherStationMessageBytes.length);

        // Add weatherMessage to the byte array
        buffer.put(weatherStationMessageBytes);

        return buffer.array();
    }


    public static Entry convertBytesToEntry(byte[] value){
        Entry entry=new Entry();

        ByteBuffer buffer = ByteBuffer.wrap(value);

        entry.setTimestamp(buffer.getLong());
        entry.setStationId(buffer.getLong());


        int weatherStationMessageByteSize= buffer.getInt();
        byte[]weatherStationMessageByte=new byte[weatherStationMessageByteSize];
        buffer.get(weatherStationMessageByte);
        WeatherStationMessage weatherStationMessage=convertByteToWeatherStationMessage(weatherStationMessageByte);

        System.out.println(weatherStationMessage.getStation_id());
        entry.setWeatherMessage(weatherStationMessage);

        return entry;
    }


    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setWeatherMessage(WeatherStationMessage weatherMessage) {
        this.weatherMessage = weatherMessage;
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
