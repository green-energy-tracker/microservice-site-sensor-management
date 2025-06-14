package com.green.energy.tracker.site_sensor_management.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ModelMapperConfigTest {

    @InjectMocks
    ModelMapperConfig modelMapperConfig;

    @Test
    void testModelMapperBean(){
        assertNotNull(modelMapperConfig.modelMapperBean());
    }
}
