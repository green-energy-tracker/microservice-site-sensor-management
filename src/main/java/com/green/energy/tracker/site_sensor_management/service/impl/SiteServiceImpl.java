package com.green.energy.tracker.site_sensor_management.service.impl;

import com.green.energy.tracker.site_sensor_management.client.UserManagementWebClientService;
import com.green.energy.tracker.site_sensor_management.kafka.KafkaSiteProducer;
import com.green.energy.tracker.site_sensor_management.model.EventType;
import com.green.energy.tracker.site_sensor_management.model.Site;
import com.green.energy.tracker.site_sensor_management.repository.SiteRepository;
import com.green.energy.tracker.site_sensor_management.service.SiteService;
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
    private final UserManagementWebClientService userManagementWebClientService;
    private final KafkaSiteProducer kafkaSiteProducer;

    @Override
    public Site create(String name, String location, String ownerUsername) {
        if(siteRepository.findByName(name).isPresent())
            throw new EntityExistsException("Site already exists with name: " + name);
        var site = siteRepository.save(Site.builder().name(name).ownerId(findOwnerIdByUsername(ownerUsername)).location(location).build());
        kafkaSiteProducer.sendMessage(EventType.CREATE,site);
        return site;
    }

    @Override
    public Site updateLocation(String name, String location) {
        var site = findByName(name);
        site.setLocation(location);
        site = siteRepository.save(site);
        kafkaSiteProducer.sendMessage(EventType.UPDATE,site);
        return site;
    }

    @Override
    public Site updateOwner(String name, String ownerUsername) {
        var site = findByName(name);
        site.setOwnerId(findOwnerIdByUsername(ownerUsername));
        site = siteRepository.save(site);
        kafkaSiteProducer.sendMessage(EventType.UPDATE,site);
        return site;
    }

    @Override
    public void delete(String name) {
        var site = findByName(name);
        siteRepository.delete(site);
        kafkaSiteProducer.sendMessage(EventType.DELETE,site);
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

    @Override
    public Long findOwnerIdByUsername(String ownerUsername) {
        return userManagementWebClientService.findUserIdByUsername(ownerUsername);
    }

}
