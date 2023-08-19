package org.example;



import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import lombok.extern.slf4j.Slf4j;


import java.io.IOError;
import java.io.IOException;
        import java.time.Duration;
        import java.util.Collections;
        import java.util.Properties;

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
    public static void kafkaConsumer() {

        Properties properties=getKafkaConsumerProps();

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("my-topic"));

        while (true) {
            try {
                ConsumerRecords<String, String> recordsBatch = consumer.poll(Duration.ofSeconds(1));

                    for (ConsumerRecord<String, String> record : recordsBatch) {
                       System.out.printf("Received message: key=%s, value=%s, partition=%d, offset=%d%n",
                                record.key(), record.value(), record.partition(), record.offset());

                       /*
                      log.info("Received message: key=%s, value=%s, partition=%d, offset=%d%n",
                              record.key(), record.value(), record.partition(), record.offset());

                        */
                }
              }
            catch (IOError e){
                throw new RuntimeException();
            }

           }
    }

    public static void main(String[] args) {
        kafkaConsumer();
    }
}
