package com.green.energy.tracker.site_sensor_management.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "user-management",
        url = "${spring.client.user-management.url}/${spring.client.user-management.version}/user-management",
        fallbackFactory = UserManagementClientFallbackFactory.class,
        qualifiers = "cbUserManagementFeignClient"
)
public interface UserManagementClient {
    @GetMapping("/findIdByUsername")
    Long findUserIdByUsername(@RequestParam("username") String username);
}
