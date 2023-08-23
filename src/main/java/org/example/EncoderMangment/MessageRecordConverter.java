package org.example.EncoderMangment;

import org.apache.avro.generic.GenericRecord;
import org.example.model.MessageModel.WeatherStationMessage;

public interface MessageRecordConverter {

   GenericRecord createEncodedRecord(WeatherStationMessage weatherStationMessage);
}
