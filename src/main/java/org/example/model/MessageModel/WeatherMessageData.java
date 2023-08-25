package org.example.model.MessageModel;

import lombok.Data;
import org.apache.avro.Schema;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;

@Data

public class WeatherMessageData implements Serializable {
    private   Integer humidity;
    private   Integer temperature;
    private   Integer wind_speed;


    public byte[] toByteArray(){
        int totalSize=4 + 4 + 4;
        // Create a new ByteBuffer with a sufficient capacity
        ByteBuffer buffer = ByteBuffer.allocate(totalSize);
        buffer.putInt(humidity);
        buffer.putInt(temperature);
        buffer.putInt(wind_speed);

     return buffer.array();
    }

    public static WeatherMessageData convertByteToWeatherDataMessage(byte[]value){
        WeatherMessageData weatherMessageData=new WeatherMessageData();

        ByteBuffer buffer = ByteBuffer.wrap(value);
        weatherMessageData.setHumidity(buffer.getInt());
        weatherMessageData.setTemperature(buffer.getInt());
        weatherMessageData.setWind_speed(buffer.getInt());

      return weatherMessageData;
    }

    public static Schema getWeatherMessageDataAvroSchema() {
        Schema schema = Schema.createRecord("WeatherMessageData", null, null, false);
        schema.setFields(Arrays.asList(
                new Schema.Field("temperature", Schema.create(Schema.Type.INT), null, null),
                new Schema.Field("humidity", Schema.create(Schema.Type.INT), null, null),
                new Schema.Field("wind_speed", Schema.create(Schema.Type.INT), null, null)
        ));
        return schema;
    }
}
