package com.green.energy.tracker.site_sensor_management.kafka;

import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.properties.schema.registry.url}")
    private String schemaRegistryUrl;
    @Value("${spring.kafka.producer-avro.key-serializer}")
    private String producerAvroKeySerializer;
    @Value("${spring.kafka.producer-avro.value-serializer}")
    private String producerAvroValueSerializer;
    @Value("${spring.kafka.producer-dlt.key-serializer}")
    private String producerDltKeySerializer;
    @Value("${spring.kafka.producer-dlt.value-serializer}")
    private String producerDltValueSerializer;


    @Bean
    public ProducerFactory<String,SpecificRecord> avroProducerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerAvroKeySerializer);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerAvroValueSerializer);
        config.put("schema.registry.url", schemaRegistryUrl);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean(name = "avroKafkaTemplate")
    public KafkaTemplate<String, SpecificRecord> avroKafkaTemplate() {
        return new KafkaTemplate<>(avroProducerFactory());
    }

    @Bean
    public ProducerFactory<String, DltRecord> dltProducerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerDltKeySerializer);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerDltValueSerializer);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean(name = "dltKafkaTemplate")
    public KafkaTemplate<String, DltRecord> dltKafkaTemplate() {
        return new KafkaTemplate<>(dltProducerFactory());
    }
}
