package com.green.energy.tracker.site_sensor_management.kafka;

import com.green.energy.tracker.configuration.domain.event.SensorEventPayload;
import com.green.energy.tracker.site_sensor_management.model.EventType;
import com.green.energy.tracker.site_sensor_management.model.Sensor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaSensorProducer {
    @Value("${spring.kafka.topic.sensor-events}")
    private String topicSensorEvents;
    @Value("${spring.kafka.topic.sensor-events-dlt}")
    private String topicSensorEventsDlt;
    private final KafkaTemplate<String,SpecificRecord> avroSensorKafkaTemplate;
    private final KafkaDltProducer kafkaDltProducer;
    private final ModelMapper modelMapper;

    public void sendMessage(EventType eventType, Sensor sensor) {
        var sensorEventPayload = modelMapper.map(sensor, SensorEventPayload.class);
        sensorEventPayload.setEventType(eventType.name());
        try{
            avroSensorKafkaTemplate.send(topicSensorEvents, String.valueOf(sensorEventPayload.getId()), sensorEventPayload)
                    .whenComplete((result, ex) -> {
                        if (Objects.nonNull(ex)) {
                            log.error("Failed to send message: {}", ex.getMessage());
                            kafkaDltProducer.sendMessage(topicSensorEventsDlt, sensorEventPayload, ex);
                        } else {
                            log.info("Message published on topic: {}, payload: {}", topicSensorEvents, sensorEventPayload);
                        }
                    });
            log.info("Message published on topic: {}, payload: {}", topicSensorEvents,sensorEventPayload);
        } catch (Exception ex) {
            kafkaDltProducer.sendMessage(topicSensorEventsDlt,sensorEventPayload,ex);
        }

    }
}
