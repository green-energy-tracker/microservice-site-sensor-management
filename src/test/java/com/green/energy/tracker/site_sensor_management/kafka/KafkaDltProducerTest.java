package com.green.energy.tracker.site_sensor_management.kafka;

import com.green.energy.tracker.configuration.domain.event.SiteEventPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import java.util.concurrent.CompletableFuture;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaDltProducerTest {

    @InjectMocks
    private KafkaDltProducer kafkaDltProducer;
    @Mock
    private KafkaTemplate<String, DltRecord> dltRecordKafkaTemplate;
    @Mock
    private CompletableFuture<SendResult<String,DltRecord>> mockResult;
    private String topic;
    private RuntimeException cause;
    private RuntimeException ex;
    private SiteEventPayload specificRecord;

    @BeforeEach
    void setUp(){
        topic =  "dlt-topic";
        cause = new RuntimeException("Root cause");
        ex = new RuntimeException("Kafka failure", cause);
        specificRecord = new SiteEventPayload();
    }

    @Test
    void sendMessageTest_success(){
        when(dltRecordKafkaTemplate.send(eq(topic), any(DltRecord.class))).thenReturn(mockResult);
        when(mockResult.thenAccept(any())).thenReturn(CompletableFuture.completedFuture(null));
        kafkaDltProducer.sendMessage(topic, specificRecord, ex);
        ArgumentCaptor<DltRecord> captor = ArgumentCaptor.forClass(DltRecord.class);
        verify(dltRecordKafkaTemplate).send(eq(topic), captor.capture());
        DltRecord recordSent = captor.getValue();
        assertEquals("SiteEventPayload", recordSent.getModel());
        assertEquals("Kafka failure", recordSent.getError());
        assertEquals("Root cause", recordSent.getCausedBy());
    }

    @Test
    void sendMessageTest_error(){
        CompletableFuture<SendResult<String, DltRecord>> failedFuture = new CompletableFuture<>();
        failedFuture.completeExceptionally(new RuntimeException("Simulated Kafka failure"));
        when(dltRecordKafkaTemplate.send(eq(topic), any(DltRecord.class))).thenReturn(failedFuture);
        kafkaDltProducer.sendMessage(topic, specificRecord, ex);
        verify(dltRecordKafkaTemplate).send(eq(topic), any(DltRecord.class));
    }
}
