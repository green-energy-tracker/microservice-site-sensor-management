package com.green.energy.tracker.site_sensor_management.service;

import com.green.energy.tracker.site_sensor_management.model.Sensor;
import com.green.energy.tracker.site_sensor_management.model.SensorStatus;
import com.green.energy.tracker.site_sensor_management.model.SensorType;
import com.green.energy.tracker.site_sensor_management.model.Site;

import java.util.List;

public interface SensorService {
    Sensor create();
    Sensor update();
    boolean delete();
    Sensor findById(Long id);
    List<Sensor> findByType(SensorType type);
    List<Sensor> findByStatus(SensorStatus status);
    List<Sensor> findBySiteId(Long siteId);
}
