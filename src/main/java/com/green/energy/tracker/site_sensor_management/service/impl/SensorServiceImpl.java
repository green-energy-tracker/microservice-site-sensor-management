package com.green.energy.tracker.site_sensor_management.service.impl;

import com.green.energy.tracker.site_sensor_management.model.Sensor;
import com.green.energy.tracker.site_sensor_management.model.SensorStatus;
import com.green.energy.tracker.site_sensor_management.model.SensorType;
import com.green.energy.tracker.site_sensor_management.repository.SensorRepository;
import com.green.energy.tracker.site_sensor_management.service.SensorService;
import com.green.energy.tracker.site_sensor_management.service.SiteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service("SensorServiceV1")
@Transactional
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;
    private final SiteService siteService;

    @Override
    public Sensor create(String siteName,String code, SensorType type, String model) throws DataIntegrityViolationException {
        var site = siteService.findByName(siteName);
        return sensorRepository.save(Sensor.builder()
                .site(site)
                .code(code)
                .type(type)
                .model(model)
                .status(SensorStatus.INACTIVE)
                .build()
        );
    }

    @Override
    public Sensor updateSite(String code, String siteName) {
        var sensor = findByCode(code);
        var site = siteService.findByName(siteName);
        sensor.setSite(site);
        return sensorRepository.save(sensor);
    }

    @Override
    public void delete(String code) {
        var sensor = findByCode(code);
        sensorRepository.delete(sensor);
    }

    @Override
    public Sensor findById(Long id) {
        return sensorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sensor not found with id: " + id));
    }

    @Override
    public Sensor findByCode(String code) {
        return sensorRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Sensor not found with code: " + code));
    }

    @Override
    public List<Sensor> findByType(SensorType type) {
        return sensorRepository.findByType(type);
    }

    @Override
    public List<Sensor> findByStatus(SensorStatus status) {
        return sensorRepository.findByStatus(status);
    }

    @Override
    public List<Sensor> findBySiteId(Long siteId) {
        return sensorRepository.findBySiteId(siteId);
    }
}
