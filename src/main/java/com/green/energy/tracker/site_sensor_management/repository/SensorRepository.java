package com.green.energy.tracker.site_sensor_management.repository;

import com.green.energy.tracker.site_sensor_management.model.Sensor;
import org.springframework.data.repository.CrudRepository;

public interface SensorRepository extends CrudRepository<Sensor,Long> {
}
