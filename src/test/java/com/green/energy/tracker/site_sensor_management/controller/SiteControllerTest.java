package com.green.energy.tracker.site_sensor_management.controller;

import com.green.energy.tracker.site_sensor_management.model.Site;
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
class SiteControllerTest {

    @Mock
    private SiteService siteService;
    @InjectMocks
    private SiteController siteController;
    private String siteName;
    private String siteLocation;
    private String ownerUsername;
    private Site siteTest;

    @BeforeEach
    void setUp() {
        siteName = "test";
        siteLocation = "test";
        ownerUsername = "test";
        siteTest = Site.builder().name(siteName).location(siteLocation).ownerId(1L).build();
    }

    @Test
    void createSite_ResponseOk() {
        when(siteService.create(siteName,siteLocation,ownerUsername)).thenReturn(siteTest);
        assertEquals(ResponseEntity.ok(siteTest),siteController.createSite(siteName,siteLocation,ownerUsername));
    }

    @Test
    void updateLocation_ResponseOk() {
        when(siteService.updateLocation(siteName,siteLocation)).thenReturn(siteTest);
        assertEquals(ResponseEntity.ok(siteTest),siteController.updateLocation(siteName,siteLocation));
    }


    @Test
    void updateOwner_ResponseOk() throws Exception {
        when(siteService.updateOwner(siteName,ownerUsername)).thenReturn(siteTest);
        assertEquals(ResponseEntity.ok(siteTest),siteController.updateOwner(siteName,ownerUsername));
    }


    @Test
    void delete_ResponseOk() {
        doNothing().when(siteService).delete(siteName);
        ResponseEntity.BodyBuilder response = siteController.delete(siteName);
        assertEquals(HttpStatus.OK, response.build().getStatusCode());
        verify(siteService, times(1)).delete(siteName);
    }


    @Test
    void getByName_ResponseOk() {
        when(siteService.findByName(siteName)).thenReturn(siteTest);
        assertEquals(ResponseEntity.ok(siteTest),siteController.getByName(siteName));
    }
}