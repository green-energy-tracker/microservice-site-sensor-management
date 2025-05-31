package com.green.energy.tracker.site_sensor_management.repository;

import com.green.energy.tracker.site_sensor_management.model.Sensor;
import com.green.energy.tracker.site_sensor_management.model.SensorStatus;
import com.green.energy.tracker.site_sensor_management.model.SensorType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SensorRepository extends CrudRepository<Sensor,Long> {
    Optional<Sensor> findByCode(String code);
    List<Sensor> findByType(SensorType type);
    List<Sensor> findByStatus(SensorStatus status);
    List<Sensor> findBySiteId(Long siteId);
}
