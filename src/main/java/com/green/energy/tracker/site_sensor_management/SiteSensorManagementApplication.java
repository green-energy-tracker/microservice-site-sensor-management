package com.green.energy.tracker.site_sensor_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SiteSensorManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiteSensorManagementApplication.class, args);
	}

}
