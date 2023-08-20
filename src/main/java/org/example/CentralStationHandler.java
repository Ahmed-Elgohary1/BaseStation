package org.example;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.example.model.WeatherStationMessage;


import java.io.IOError;
import java.io.IOException;
        import java.time.Duration;
        import java.util.Collections;
        import java.util.Properties;
import java.util.logging.Logger;

public class CentralStationHandler {

    private static Properties getKafkaConsumerProps(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "my-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        return props;
    }

    private static final Logger log = Logger.getLogger(CentralStationHandler.class.getName());


    public static void kafkaConsumer() {




        Properties properties=getKafkaConsumerProps();

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("my-topic"));

        ObjectMapper mapper = new ObjectMapper();

        while (true) {
            try {
                ConsumerRecords<String, String> recordsBatch = consumer.poll(Duration.ofSeconds(1));

                    for (ConsumerRecord<String, String> record : recordsBatch) {


                        WeatherStationMessage message = mapper.readValue(record.value(), WeatherStationMessage.class);
                        System.out.printf(String.valueOf(message));



                }
              }
            catch (IOError e){
                throw new RuntimeException();
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static void main(String[] args) {
        kafkaConsumer();
    }
}
