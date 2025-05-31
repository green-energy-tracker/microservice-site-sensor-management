package com.green.energy.tracker.site_sensor_management.service;

import com.green.energy.tracker.site_sensor_management.model.Site;

import java.util.List;

public interface SiteService {
    Site create(String name, String location, String ownerUsername);
    Site updateLocation(String name, String location);
    Site updateOwner(String name, String ownerUsername);
    void delete(String name);
    Site findById(Long id);
    Site findByName(String name);
    List<Site> findByOwnerId(Long ownerId);
    Long findOwnerIdByUsername(String ownerUsername);
}
