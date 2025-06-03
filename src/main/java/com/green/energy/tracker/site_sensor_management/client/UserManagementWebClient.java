package com.green.energy.tracker.site_sensor_management.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface UserManagementWebClient {
    @GetExchange("/findIdByUsername")
    Long findUserIdByUsername(@RequestParam String username);
}
