package com.green.energy.tracker.site_sensor_management.kafka;

import com.green.energy.tracker.configuration.domain.event.SiteEventPayload;
import com.green.energy.tracker.site_sensor_management.model.EventType;
import com.green.energy.tracker.site_sensor_management.model.Site;
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
public class KafkaSiteProducer {
    @Value("${spring.kafka.topic.site-events}")
    private String topicSiteEvents;
    private final KafkaTemplate<String, SiteEventPayload> avroSiteKafkaTemplate;
    private final ModelMapper modelMapper;

    public void sendMessage(EventType eventType, Site site) throws ExecutionException, InterruptedException {
        var siteEventPayload = modelMapper.map(site, SiteEventPayload.class);
        siteEventPayload.setEventType(eventType.name());
        avroSiteKafkaTemplate.send(topicSiteEvents, String.valueOf(siteEventPayload.getId()), siteEventPayload).get();
    }
}
