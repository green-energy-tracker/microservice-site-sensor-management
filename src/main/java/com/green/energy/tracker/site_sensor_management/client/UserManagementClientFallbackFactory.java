package com.green.energy.tracker.site_sensor_management.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

@Slf4j
public class UserManagementClientFallbackFactory implements FallbackFactory<UserManagementClient> {
    @Override
    public UserManagementClient create(Throwable cause) {
        log.error(cause.getMessage());
        return username -> {throw new IllegalStateException("UserManagement service not reachable for username='" + username + "'", cause);};
    }
}
