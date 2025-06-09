package com.green.energy.tracker.site_sensor_management.service.impl;

import com.green.energy.tracker.site_sensor_management.client.UserManagementWebClientService;
import com.green.energy.tracker.site_sensor_management.kafka.KafkaSiteProducer;
import com.green.energy.tracker.site_sensor_management.model.Site;
import com.green.energy.tracker.site_sensor_management.repository.SiteRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SiteServiceImplTest {
    @InjectMocks
    private SiteServiceImpl siteService;
    @Mock
    private SiteRepository siteRepository;
    @Mock
    private UserManagementWebClientService userManagementWebClientService;
    @Mock
    private KafkaSiteProducer kafkaSiteProducer;
    private Site mockSite;

    @BeforeEach
    void setUp(){
        mockSite = Site.builder().name("test").ownerId(1L).location("test").sensors(Collections.emptyList()).build();
    }

    @Test
    void testCreate() {
        when(userManagementWebClientService.findUserIdByUsername("test")).thenReturn(1L);
        when(siteRepository.findByName("test")).thenReturn(Optional.empty());
        siteService.create("test", "test", "test");
        verify(siteRepository).findByName("test");
        verify(siteRepository).save(mockSite);
    }

    @Test
    void testCreateEntityExistsException() {
        when(siteRepository.findByName("test")).thenReturn(Optional.of(mockSite));
        assertThrows(EntityExistsException.class, () -> siteService.create("test", "test", "test"));
        verify(siteRepository).findByName("test");
        verify(siteRepository, never()).save(any());
    }

    @Test
    void testUpdateLocation() {
        when(siteRepository.findByName("test")).thenReturn(Optional.of(mockSite));
        siteService.updateLocation("test","test1");
        verify(siteRepository).findByName("test");
        verify(siteRepository).save(mockSite);
    }

    @Test
    void testUpdateLocationEntityNotFoundException() {
        when(siteRepository.findByName("test")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> siteService.updateLocation("test","test"));
        verify(siteRepository).findByName("test");
        verify(siteRepository, never()).save(any());
    }

    @Test
    void testUpdateOwner() {
        when(siteRepository.findByName("test")).thenReturn(Optional.of(mockSite));
        siteService.updateOwner("test","test");
        verify(siteRepository).findByName("test");
        verify(siteRepository).save(mockSite);
    }

    @Test
    void testUpdateOwnerEntityNotFoundException() {
        when(siteRepository.findByName("test")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> siteService.updateOwner("test","test"));
        verify(siteRepository).findByName("test");
        verify(siteRepository, never()).save(any());
    }

    @Test
    void testDelete() {
        when(siteRepository.findByName("test")).thenReturn(Optional.of(mockSite));
        siteService.delete("test");
        verify(siteRepository).findByName("test");
        verify(siteRepository).delete(mockSite);
    }

    @Test
    void testDeleteEntityNotFoundException() {
        when(siteRepository.findByName("test")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> siteService.delete("test"));
        verify(siteRepository).findByName("test");
        verify(siteRepository, never()).delete(any());
    }

    @Test
    void testFindById() {
        when(siteRepository.findById(1L)).thenReturn(Optional.of(mockSite));
        Site site = siteService.findById(1L);
        assertNotNull(site);
        verify(siteRepository).findById(1L);
    }

    @Test
    void testFindByIdEntityNotFoundException() {
        when(siteRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> siteService.findById(2L));
        verify(siteRepository).findById(2L);
    }

    @Test
    void testFindByOwnerId() {
        when(siteRepository.findByOwnerId(1L)).thenReturn(List.of(mockSite));
        var site = siteService.findByOwnerId(1L);
        assertFalse(site.isEmpty());
        verify(siteRepository).findByOwnerId(1L);
    }

}
