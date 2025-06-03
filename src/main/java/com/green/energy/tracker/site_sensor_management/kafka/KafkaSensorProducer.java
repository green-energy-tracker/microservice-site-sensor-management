package com.green.energy.tracker.site_sensor_management.kafka;

import com.green.energy.tracker.configuration.domain.event.SensorEventPayload;
import com.green.energy.tracker.site_sensor_management.model.EventType;
import com.green.energy.tracker.site_sensor_management.model.Sensor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaSensorProducer {
    @Value("${spring.kafka.topic.sensor-events}")
    private String topicSensorEvents;
    private final KafkaTemplate<String, SensorEventPayload> avroSensorKafkaTemplate;
    private final ModelMapper modelMapper;

    public void sendMessage(EventType eventType, Sensor sensor) throws ExecutionException, InterruptedException {
        var sensorEventPayload = modelMapper.map(sensor, SensorEventPayload.class);
        sensorEventPayload.setEventType(eventType.name());
        avroSensorKafkaTemplate.send(topicSensorEvents, String.valueOf(sensorEventPayload.getId()), sensorEventPayload).get();
    }
}
