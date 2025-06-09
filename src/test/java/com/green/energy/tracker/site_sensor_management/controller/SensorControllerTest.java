package com.green.energy.tracker.site_sensor_management.controller;

import com.green.energy.tracker.site_sensor_management.model.Sensor;
import com.green.energy.tracker.site_sensor_management.model.SensorStatus;
import com.green.energy.tracker.site_sensor_management.model.SensorType;
import com.green.energy.tracker.site_sensor_management.model.Site;
import com.green.energy.tracker.site_sensor_management.service.SensorService;
import com.green.energy.tracker.site_sensor_management.service.SiteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SensorControllerTest {

    @Mock
    private SensorService sensorService;
    @InjectMocks
    private SensorController sensorController;
    @Mock
    private Site site;
    private String siteName;
    private String code;
    private String model;
    private Sensor sensorTest;

    @BeforeEach
    void setUp() {
        siteName = "test";
        code = "test";
        model = "test";
        sensorTest = Sensor.builder().site(site).code(code).type(SensorType.SOLAR).model(model).status(SensorStatus.INACTIVE).build();
    }

    @Test
    void createSensor_ResponseOk() {
        when(sensorService.create(siteName,code,SensorType.SOLAR,model)).thenReturn(sensorTest);
        assertEquals(ResponseEntity.ok(sensorTest),sensorController.createSensor(siteName,code,SensorType.SOLAR,model));
    }

    @Test
    void updateSite_ResponseOk() {
        when(sensorService.updateSite(code,siteName)).thenReturn(sensorTest);
        assertEquals(ResponseEntity.ok(sensorTest),sensorController.updateSite(code,siteName));
    }

    @Test
    void delete_ResponseOk() {
        doNothing().when(sensorService).delete(code);
        var response = sensorController.delete(code);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(sensorService, times(1)).delete(code);
    }


    @Test
    void getByCode_ResponseOk() {
        when(sensorService.findByCode(code)).thenReturn(sensorTest);
        assertEquals(ResponseEntity.ok(sensorTest),sensorController.getByCode(code));
    }
}