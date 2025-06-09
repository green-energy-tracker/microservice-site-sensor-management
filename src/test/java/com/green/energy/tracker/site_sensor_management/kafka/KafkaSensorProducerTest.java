package com.green.energy.tracker.site_sensor_management.kafka;

import com.green.energy.tracker.configuration.domain.event.SensorEventPayload;
import com.green.energy.tracker.site_sensor_management.model.EventType;
import com.green.energy.tracker.site_sensor_management.model.Sensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KafkaSensorProducerTest {

    @InjectMocks
    KafkaSensorProducer kafkaSensorProducer;
    @Mock
    private KafkaTemplate<String, SensorEventPayload> avroSensorKafkaTemplate;
    @Mock
    private KafkaDltProducer kafkaDltProducer;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private Sensor sensor;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(kafkaSensorProducer,"topicSensorEvents", "topic-test");
        ReflectionTestUtils.setField(kafkaSensorProducer,"topicSensorEventsDlt", "topic-test-dlt");
    }

    @Test
    void sendMessageTest_success(){
        SensorEventPayload payload = new SensorEventPayload();
        payload.setId(123L);
        when(modelMapper.map(sensor, SensorEventPayload.class)).thenReturn(payload);
        when(avroSensorKafkaTemplate.send(anyString(), anyString(), any(SensorEventPayload.class)))
                .thenReturn(CompletableFuture.completedFuture(null));
        kafkaSensorProducer.sendMessage(EventType.CREATE, sensor);
        verify(avroSensorKafkaTemplate).send("topic-test", "123", payload);
        verifyNoInteractions(kafkaDltProducer);
    }

    @Test
    void sendMessageTest_error() {
        SensorEventPayload payload = new SensorEventPayload();
        payload.setId(456L);
        var ex = new RuntimeException("Kafka send failed");
        when(modelMapper.map(sensor, SensorEventPayload.class)).thenReturn(payload);
        when(avroSensorKafkaTemplate.send("topic-test", String.valueOf(payload.getId()), payload)).thenThrow(ex);
        kafkaSensorProducer.sendMessage(EventType.UPDATE, sensor);
        verify(kafkaDltProducer).sendMessage("topic-test-dlt", payload,ex);
    }

}
