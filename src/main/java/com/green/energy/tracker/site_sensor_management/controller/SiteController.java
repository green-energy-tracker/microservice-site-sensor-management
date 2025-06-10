package com.green.energy.tracker.site_sensor_management.controller;

import com.green.energy.tracker.site_sensor_management.model.Sensor;
import com.green.energy.tracker.site_sensor_management.model.Site;
import com.green.energy.tracker.site_sensor_management.service.SiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/site-management/site")
public class SiteController {

    private final SiteService siteService;

    @Operation(summary = "Create Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Site created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Owner user not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "503", description = "User Management Service unavailable", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<Site> createSite(@RequestParam String name, @RequestParam String location, @RequestParam String ownerUsername) {
        return ResponseEntity.ok(siteService.create(name,location,ownerUsername));
    }

    @Operation(summary = "Update Location Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Site location updated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Site not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PatchMapping("/updateLocation")
    public ResponseEntity<Site> updateLocation(@RequestParam String name, @RequestParam String location) {
        return ResponseEntity.ok(siteService.updateLocation(name,location));
    }

    @Operation(summary = "Update Owner Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Site owner updated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Site not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "503", description = "User Management Service unavailable", content = @Content)
    })
    @PatchMapping("/updateOwner")
    public ResponseEntity<Site> updateOwner(@RequestParam String name, @RequestParam String ownerUsername) {
        return ResponseEntity.ok(siteService.updateOwner(name,ownerUsername));
    }

    @Operation(summary = "Delete Site and All Sensors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Site deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Site not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @DeleteMapping("/delete")
    public ResponseEntity.BodyBuilder delete(@RequestParam String name) {
        siteService.delete(name);
        return ResponseEntity.status(HttpStatus.OK);
    }

    @Operation(summary = "Get Site Info by Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Site found"),
            @ApiResponse(responseCode = "400", description = "Invalid name parameter", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Site not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/findByName")
    public ResponseEntity<Site> getByName(@RequestParam String name) {
        return ResponseEntity.ok(siteService.findByName(name));
    }

    @Operation(summary = "Get Site Info by Sensor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Site found"),
            @ApiResponse(responseCode = "400", description = "Invalid sensor parameter", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Site not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping("/findBySensor")
    public ResponseEntity<Site> getBySensor(@RequestBody Sensor sensor) {
        return ResponseEntity.ok(siteService.findBySensor(sensor));
    }
}
