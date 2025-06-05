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

    public void sendMessage(String topicDlt, ProducerRecord<String,? extends SpecificRecord> producerRecord, Throwable ex) {
        var dltMessage = DltRecord.builder()
                .key(Objects.nonNull(producerRecord.key()) ? producerRecord.key() : "")
                .payload(Objects.nonNull(producerRecord.value()) ? producerRecord.value().toString() : "")
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
