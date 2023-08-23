package org.example.model.BitCaskModel;

import org.example.model.MessageModel.WeatherStationMessage;

public class FileEntry {
    private Long timestamp;
    private Long stationId;
    private WeatherStationMessage weatherMessage;


    public FileEntry(Long timestamp,Long stationId,WeatherStationMessage weatherStationMessage){
        this.stationId=stationId;
        this.timestamp=timestamp;
        this.weatherMessage=weatherStationMessage;
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
