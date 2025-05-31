package com.green.energy.tracker.site_sensor_management.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user-management",
        url = "${spring.client.user-management.url}/${spring.client.user-management.version}/user-management"
)
public interface UserManagementClient {
    @GetMapping("/findIdByUsername/{username}")
    Long findUserIdByUsername(@PathVariable("username") String username);
}
