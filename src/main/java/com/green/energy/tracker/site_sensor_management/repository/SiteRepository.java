package com.green.energy.tracker.site_sensor_management.repository;

import com.green.energy.tracker.site_sensor_management.model.Site;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SiteRepository extends CrudRepository<Site,Long> {
    List<Site> findByOwnerId();
}
