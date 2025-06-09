package com.green.energy.tracker.site_sensor_management.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserManagementWebClientServiceTest {

    @Mock
    UserManagementWebClient userManagementWebClient;
    @InjectMocks
    UserManagementWebClientService userManagementWebClientService;

    @Test
    void findUserIdByUsernameTest(){
        when(userManagementWebClient.findUserIdByUsername("test")).thenReturn(1L);
        long userId = userManagementWebClientService.findUserIdByUsername("test");
        assertTrue(userId>0);
        verify(userManagementWebClient).findUserIdByUsername("test");
    }

    @Test
    void findUserIdByUsernameFallbackTest(){
        String username = "test";
        RuntimeException cause = new RuntimeException("Timeout");
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userManagementWebClientService.findUserIdByUsernameFallback(username, cause));
        assertNotNull(exception.getReason());
        assertTrue(exception.getReason().contains("User management service is currently unavailable"));
        assertTrue(exception.getReason().contains("test"));
        assertTrue(exception.getReason().contains("Timeout"));
    }
}
