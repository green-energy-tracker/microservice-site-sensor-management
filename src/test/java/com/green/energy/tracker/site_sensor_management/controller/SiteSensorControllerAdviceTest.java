package com.green.energy.tracker.site_sensor_management.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SiteSensorControllerAdviceTest {
    @Mock
    private HttpServletRequest httpServletRequest;
    @InjectMocks
    private SiteSensorControllerAdvice siteSensorControllerAdvice;

    @BeforeEach
    void setUp(){
        when(httpServletRequest.getRequestURI()).thenReturn("test");
    }

    @Test
    void testHandleNotFound(){
        var ex = new EntityNotFoundException();
        var response = siteSensorControllerAdvice.handleNotFound(ex,httpServletRequest);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        var problemDetail = response.getBody();
        assertEquals(ex.getMessage(),problemDetail.getDetail());
    }

    @Test
    void testHandleBadRequest(){
        var ex = new IllegalArgumentException();
        var response = siteSensorControllerAdvice.handleBadRequest(ex,httpServletRequest);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        var problemDetail = response.getBody();
        assertEquals(ex.getMessage(),problemDetail.getDetail());
    }

    @Test
    void testHandleResponseStatus(){
        var ex = new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
        var response = siteSensorControllerAdvice.handleResponseStatusException(ex,httpServletRequest);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        var problemDetail = response.getBody();
        assertEquals(ex.getMessage(),problemDetail.getDetail());
    }
}
