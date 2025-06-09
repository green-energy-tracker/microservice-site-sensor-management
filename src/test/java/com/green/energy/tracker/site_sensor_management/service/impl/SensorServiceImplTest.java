package com.green.energy.tracker.site_sensor_management.service.impl;

import com.green.energy.tracker.site_sensor_management.client.UserManagementWebClientService;
import com.green.energy.tracker.site_sensor_management.kafka.KafkaSensorProducer;
import com.green.energy.tracker.site_sensor_management.model.Sensor;
import com.green.energy.tracker.site_sensor_management.model.SensorStatus;
import com.green.energy.tracker.site_sensor_management.model.SensorType;
import com.green.energy.tracker.site_sensor_management.model.Site;
import com.green.energy.tracker.site_sensor_management.repository.SensorRepository;
import com.green.energy.tracker.site_sensor_management.service.SiteService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SensorServiceImplTest {
    @InjectMocks
    private SensorServiceImpl sensorService;
    @Mock
    private SensorRepository sensorRepository;
    @Mock
    private UserManagementWebClientService userManagementWebClientService;
    @Mock
    private KafkaSensorProducer kafkaSensorProducer;
    @Mock
    private Site site;
    @Mock
    private SiteService siteService;
    private Sensor mockSensor;

    @BeforeEach
    void setUp(){
        mockSensor = Sensor.builder().site(site).code("test").type(SensorType.SOLAR).model("test").status(SensorStatus.INACTIVE).build();
    }

    @Test
    void testCreate() {
        when(siteService.findByName("test")).thenReturn(site);
        when(sensorRepository.findByCode("test")).thenReturn(Optional.empty());
        sensorService.create("test", "test", SensorType.SOLAR,"test");
        verify(sensorRepository).findByCode("test");
        verify(sensorRepository).save(mockSensor);
    }

    @Test
    void testCreateEntityExistsException() {
        when(sensorRepository.findByCode("test")).thenReturn(Optional.of(mockSensor));
        assertThrows(EntityExistsException.class, () -> sensorService.create("test", "test", SensorType.SOLAR,"test"));
        verify(sensorRepository).findByCode("test");
        verify(sensorRepository, never()).save(any());
    }

    @Test
    void testUpdateSite() {
        when(sensorRepository.findByCode("test")).thenReturn(Optional.of(mockSensor));
        sensorService.updateSite("test","test1");
        verify(sensorRepository).findByCode("test");
        verify(sensorRepository).save(mockSensor);
    }

    @Test
    void testUpdateLocationEntityNotFoundException() {
        when(sensorRepository.findByCode("test")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> sensorService.updateSite("test","test"));
        verify(sensorRepository).findByCode("test");
        verify(sensorRepository, never()).save(any());
    }


    @Test
    void testDelete() {
        when(sensorRepository.findByCode("test")).thenReturn(Optional.of(mockSensor));
        sensorService.delete("test");
        verify(sensorRepository).findByCode("test");
        verify(sensorRepository).delete(mockSensor);
    }

    @Test
    void testDeleteEntityNotFoundException() {
        when(sensorRepository.findByCode("test")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> sensorService.delete("test"));
        verify(sensorRepository).findByCode("test");
        verify(sensorRepository, never()).delete(any());
    }

    @Test
    void testFindById() {
        when(sensorRepository.findById(1L)).thenReturn(Optional.of(mockSensor));
        Sensor sensor = sensorService.findById(1L);
        assertNotNull(sensor);
        verify(sensorRepository).findById(1L);
    }

    @Test
    void testFindByIdEntityNotFoundException() {
        when(sensorRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> sensorService.findById(2L));
        verify(sensorRepository).findById(2L);
    }

    @Test
    void testFindByCode() {
        when(sensorRepository.findByCode("test")).thenReturn(Optional.of(mockSensor));
        var sensor = sensorService.findByCode("test");
        assertNotNull(sensor);
        verify(sensorRepository).findByCode("test");
    }

    @Test
    void testFindByCodeEntityNotFoundException() {
        when(sensorRepository.findByCode("test")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> sensorService.findByCode("test"));
        verify(sensorRepository).findByCode("test");
    }

    @Test
    void testFindByType() {
        when(sensorRepository.findByType(SensorType.SOLAR)).thenReturn(List.of(mockSensor));
        var site = sensorService.findByType(SensorType.SOLAR);
        assertFalse(site.isEmpty());
        verify(sensorRepository).findByType(SensorType.SOLAR);
    }

    @Test
    void testFindByStatus() {
        when(sensorRepository.findByStatus(SensorStatus.INACTIVE)).thenReturn(List.of(mockSensor));
        var site = sensorService.findByStatus(SensorStatus.INACTIVE);
        assertFalse(site.isEmpty());
        verify(sensorRepository).findByStatus(SensorStatus.INACTIVE);
    }

    @Test
    void testFindBySiteId() {
        when(sensorRepository.findBySiteId(1L)).thenReturn(List.of(mockSensor));
        var site = sensorService.findBySiteId(1L);
        assertFalse(site.isEmpty());
        verify(sensorRepository).findBySiteId(1L);
    }
}
