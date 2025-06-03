package com.green.energy.tracker.site_sensor_management.client;

public class UserManagementUnavailableException extends RuntimeException {
    public UserManagementUnavailableException(String message) {
        super(message);
    }

    public UserManagementUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
