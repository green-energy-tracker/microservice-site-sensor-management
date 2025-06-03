package com.green.energy.tracker.site_sensor_management.service.impl;

import com.green.energy.tracker.site_sensor_management.client.UserManagementClient;
import com.green.energy.tracker.site_sensor_management.model.Site;
import com.green.energy.tracker.site_sensor_management.repository.SiteRepository;
import com.green.energy.tracker.site_sensor_management.service.SiteService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service("SiteServiceV1")
@Transactional
@Slf4j
public class SiteServiceImpl implements SiteService {

    private final SiteRepository siteRepository;
    private final UserManagementClient userManagementClient;

    @Override
    public Site create(String name, String location, String ownerUsername) {
        if(siteRepository.findByName(name).isPresent())
            throw new EntityExistsException("Site already exists with name: " + name);
        return siteRepository.save(
                Site.builder()
                        .name(name)
                        .ownerId(findOwnerIdByUsername(ownerUsername))
                        .location(location)
                        .build()
        );
    }

    @Override
    public Site updateLocation(String name, String location) {
        var site = findByName(name);
        site.setLocation(location);
        return siteRepository.save(site);
    }

    @Override
    public Site updateOwner(String name, String ownerUsername) {
        var site = findByName(name);
        site.setOwnerId(findOwnerIdByUsername(ownerUsername));
        return siteRepository.save(site);
    }

    @Override
    public void delete(String name) {
        var site = findByName(name);
        siteRepository.delete(site);
    }

    @Override
    public Site findById(Long id) {
        return siteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Site not found with id: " + id));
    }

    @Override
    public Site findByName(String name) {
        return siteRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Site not found with name: " + name));
    }

    @Override
    public List<Site> findByOwnerId(Long ownerId) {
        return siteRepository.findByOwnerId(ownerId);
    }
    @CircuitBreaker(name = "user-management", fallbackMethod = "fallback")
    @Override
    public Long findOwnerIdByUsername(String ownerUsername) {
        return userManagementClient.findUserIdByUsername(ownerUsername);
    }
    public Long fallback(String username, Throwable t) {
        log.warn("Fallback attivato: {}", t.getMessage());
        return -1L;
    }
}
