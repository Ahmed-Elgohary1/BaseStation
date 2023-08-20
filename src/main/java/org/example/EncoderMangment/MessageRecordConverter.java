package org.example.EncoderMangment;

import org.apache.avro.generic.GenericRecord;
import org.example.model.WeatherStationMessage;

public interface MessageRecordConverter {

   GenericRecord createEncodedRecord(WeatherStationMessage weatherStationMessage);
}
