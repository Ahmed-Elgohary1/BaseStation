package org.example.model.MessageModel;

import lombok.Data;
import org.apache.avro.Schema;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.example.model.MessageModel.WeatherMessageData.convertByteToWeatherDataMessage;

@Data
public class WeatherStationMessage implements Serializable {

   private Long station_id;
   private Long s_no;
   private String battery_status;
   private Long status_timestamp;

   private WeatherMessageData weatherMessageData;


   public  byte[] toByteArray() {


      byte[] weatherMessageDataBytes=weatherMessageData.toByteArray();

      int totalSize=8 + 8+ 4+battery_status.getBytes(StandardCharsets.UTF_8).length+8+4+ weatherMessageDataBytes.length;



      // Create a new ByteBuffer with a sufficient capacity
      ByteBuffer buffer = ByteBuffer.allocate(totalSize);

      // Add the fields of the WeatherStationMessage object to the buffer
      buffer.putLong(station_id);
      buffer.putLong(s_no);
      buffer.putInt(battery_status.getBytes(StandardCharsets.UTF_8).length);
      buffer.put(battery_status.getBytes(StandardCharsets.UTF_8));
      buffer.putLong(status_timestamp);


      // Add the byte array generated from the WeatherMessageData object to the buffer
      buffer.putInt(weatherMessageDataBytes.length);
      buffer.put(weatherMessageDataBytes);

      // Convert the ByteBuffer to a byte array
      byte[] byteArray = buffer.array();

      return byteArray;
   }

   public static WeatherStationMessage convertByteToWeatherStationMessage(byte[]value){
      WeatherStationMessage weatherStationMessage=new WeatherStationMessage();

      ByteBuffer buffer = ByteBuffer.wrap(value);
      weatherStationMessage.setStation_id(buffer.getLong());
      weatherStationMessage.setS_no(buffer.getLong());

      int batteryStatusLength = buffer.getInt();
      // Get the length of the file name
      byte[] batteryStatusBytes = new byte[batteryStatusLength];
      buffer.get(batteryStatusBytes); // Get the bytes representing the file name
      String batteryStatus = new String(batteryStatusBytes, StandardCharsets.UTF_8); // Convert bytes to String

      weatherStationMessage.setBattery_status(batteryStatus);

      weatherStationMessage.setStatus_timestamp(buffer.getLong());

      int weatherDataMessageByteSize= buffer.getInt();
      byte[]weatherDataMessageByte=new byte[weatherDataMessageByteSize];
      buffer.get(weatherDataMessageByte);
      WeatherMessageData weatherMessageData=convertByteToWeatherDataMessage(weatherDataMessageByte);

      weatherStationMessage.setWeatherMessageData(weatherMessageData);



      return weatherStationMessage;
   }


   public static Schema getWeatherStationMessageAvroSchema() {

      Schema schema = Schema.createRecord("WeatherStationMessage", null, null, false);
      schema.setFields(Arrays.asList(
              new Schema.Field("station_id", Schema.create(Schema.Type.LONG), null, null),
              new Schema.Field("s_no", Schema.create(Schema.Type.LONG), null, null),
              new Schema.Field("battery_status", Schema.create(Schema.Type.STRING), null, null),
              new Schema.Field("status_timestamp", Schema.create(Schema.Type.LONG), null, null),
              new Schema.Field("weatherMessageData", WeatherMessageData.getWeatherMessageDataAvroSchema(), null, null)
      ));
      return schema;
   }
}
