package com.green.energy.tracker.site_sensor_management.repository;

import com.green.energy.tracker.site_sensor_management.model.Site;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SiteRepository extends CrudRepository<Site,Long> {
    Optional<Site> findByName(String name);
    List<Site> findByOwnerId(Long ownerId);
}
