package com.green.energy.tracker.site_sensor_management.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserManagementClientFallbackFactory implements FallbackFactory<UserManagementClient> {
    @Override
    public UserManagementClient create(Throwable cause) {
        log.error("Fallback triggered due to: {}", cause.toString());
        return username -> {throw new UserManagementUnavailableException("UserManagement service not reachable, clause: " + cause);};
    }
}
