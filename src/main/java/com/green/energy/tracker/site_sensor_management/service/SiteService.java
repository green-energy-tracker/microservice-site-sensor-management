package com.green.energy.tracker.site_sensor_management.service;

import com.green.energy.tracker.site_sensor_management.model.Site;

import java.util.List;

public interface SiteService {
    Site create();
    Site update();
    boolean delete();
    Site findById(Long id);
    List<Site> findByOwnerId(Long ownerId);
}
