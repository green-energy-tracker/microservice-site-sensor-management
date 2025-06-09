package com.green.energy.tracker.site_sensor_management.kafka;

import com.green.energy.tracker.configuration.domain.event.SiteEventPayload;
import com.green.energy.tracker.site_sensor_management.model.EventType;
import com.green.energy.tracker.site_sensor_management.model.Site;
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
public class KafkaSiteProducer {
    @Value("${spring.kafka.topic.site-events}")
    private String topicSiteEvents;
    @Value("${spring.kafka.topic.site-events-dlt}")
    private String topicSiteEventsDlt;
    private final KafkaTemplate<String, SpecificRecord> avroSiteKafkaTemplate;
    private final KafkaDltProducer kafkaDltProducer;
    private final ModelMapper modelMapper;

    public void sendMessage(EventType eventType, Site site) {
        var siteEventPayload = modelMapper.map(site, SiteEventPayload.class);
        siteEventPayload.setEventType(eventType.name());
        try{
            avroSiteKafkaTemplate.send(topicSiteEvents, String.valueOf(siteEventPayload.getId()), siteEventPayload)
                    .whenComplete((result, ex) -> {
                        if (Objects.nonNull(ex)) {
                            log.error("Failed to send message: {}", ex.getMessage());
                            kafkaDltProducer.sendMessage(topicSiteEventsDlt, siteEventPayload, ex);
                        } else {
                            log.info("Message published on topic: {}, payload: {}", topicSiteEvents, siteEventPayload);
                        }
                    });
            log.info("Message published on topic: {}, payload: {}", topicSiteEvents,siteEventPayload);
        } catch (Exception ex) {
            kafkaDltProducer.sendMessage(topicSiteEventsDlt,siteEventPayload,ex);
        }
    }
}
