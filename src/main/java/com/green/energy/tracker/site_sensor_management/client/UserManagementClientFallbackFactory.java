package com.green.energy.tracker.site_sensor_management.client;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserManagementClientFallbackFactory implements FallbackFactory<UserManagementClient> {

    @Override
    public UserManagementClient create(Throwable cause) {
        return new UserManagementClient(){
            @Override
            public Long findUserIdByUsername(String username) {
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "User-management unavailable");
            }
        };
    }
}