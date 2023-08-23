package org.example.model.MessageModel;

import lombok.Data;
import org.apache.avro.Schema;

import java.util.Arrays;

@Data
public class WeatherStationMessage {

   private Long station_id;
   private Long s_no;
   private String battery_status;
   private Long status_timestamp;

   private WeatherMessageData weatherMessageData;



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
