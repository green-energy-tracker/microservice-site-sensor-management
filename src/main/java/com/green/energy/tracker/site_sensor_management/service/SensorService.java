package com.green.energy.tracker.site_sensor_management.service;

import com.green.energy.tracker.site_sensor_management.model.Sensor;
import com.green.energy.tracker.site_sensor_management.model.SensorStatus;
import com.green.energy.tracker.site_sensor_management.model.SensorType;

import java.util.List;

public interface SensorService {
    Sensor create(String siteName, String code, SensorType type, String model);
    Sensor updateSite(String code, String siteName);
    void delete(String code);
    Sensor findById(Long id);
    Sensor findByCode(String code);
    List<Sensor> findByType(SensorType type);
    List<Sensor> findByStatus(SensorStatus status);
    List<Sensor> findBySiteId(Long siteId);
}
