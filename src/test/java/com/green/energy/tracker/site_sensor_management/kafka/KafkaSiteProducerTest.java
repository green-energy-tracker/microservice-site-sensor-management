package com.green.energy.tracker.site_sensor_management.kafka;

import com.green.energy.tracker.configuration.domain.event.SiteEventPayload;
import com.green.energy.tracker.site_sensor_management.model.EventType;
import com.green.energy.tracker.site_sensor_management.model.Site;
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
public class KafkaSiteProducerTest {

    @InjectMocks
    KafkaSiteProducer kafkaSiteProducer;
    @Mock
    private KafkaTemplate<String, SiteEventPayload> avroSiteKafkaTemplate;
    @Mock
    private KafkaDltProducer kafkaDltProducer;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private Site site;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(kafkaSiteProducer,"topicSiteEvents", "topic-test");
        ReflectionTestUtils.setField(kafkaSiteProducer,"topicSiteEventsDlt", "topic-test-dlt");
    }

    @Test
    void sendMessageTest_success(){
        SiteEventPayload payload = new SiteEventPayload();
        payload.setId(123L);
        when(modelMapper.map(site, SiteEventPayload.class)).thenReturn(payload);
        when(avroSiteKafkaTemplate.send(anyString(), anyString(), any(SiteEventPayload.class)))
                .thenReturn(CompletableFuture.completedFuture(null));
        kafkaSiteProducer.sendMessage(EventType.CREATE, site);
        verify(avroSiteKafkaTemplate).send("topic-test", "123", payload);
        verifyNoInteractions(kafkaDltProducer);
    }

    @Test
    void sendMessageTest_error() {
        SiteEventPayload payload = new SiteEventPayload();
        payload.setId(456L);
        var ex = new RuntimeException("Kafka send failed");
        when(modelMapper.map(site, SiteEventPayload.class)).thenReturn(payload);
        when(avroSiteKafkaTemplate.send("topic-test", String.valueOf(payload.getId()), payload)).thenThrow(ex);
        kafkaSiteProducer.sendMessage(EventType.UPDATE, site);
        verify(kafkaDltProducer).sendMessage("topic-test-dlt", payload,ex);
    }

}
