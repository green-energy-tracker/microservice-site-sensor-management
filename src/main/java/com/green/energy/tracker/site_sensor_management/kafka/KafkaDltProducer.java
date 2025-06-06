package com.green.energy.tracker.site_sensor_management.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaDltProducer {

    private final KafkaTemplate<String, DltRecord> dltRecordKafkaTemplate;

    public <T extends SpecificRecord> void sendMessage(String topicDlt, T specificRecord, Throwable ex) {
        var dltMessage = DltRecord.builder()
                .model(specificRecord.getClass().getSimpleName())
                .payload(specificRecord.toString())
                .error(ex.getMessage())
                .causedBy(ex.getCause().getMessage())
                .build();
        dltRecordKafkaTemplate.send(topicDlt,dltMessage)
                .thenAccept(result ->
                        log.info("Message published on topic {} offset={}", result.getRecordMetadata().topic(), result.getRecordMetadata().offset()))
                .exceptionally(error -> {
                    log.error("Error while sending message to Kafka topic {}: {}", topicDlt, error.getMessage(), error);
                    return null;
                });;
    }
}
