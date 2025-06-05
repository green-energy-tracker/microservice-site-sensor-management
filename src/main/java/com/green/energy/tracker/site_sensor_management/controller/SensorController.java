package com.green.energy.tracker.site_sensor_management.controller;

import com.green.energy.tracker.site_sensor_management.model.Sensor;
import com.green.energy.tracker.site_sensor_management.model.SensorType;
import com.green.energy.tracker.site_sensor_management.service.SensorService;
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
@RequestMapping("/api/v1/site-management/sensor")
public class SensorController {

    private final SensorService sensorService;

    @Operation(summary = "Create Sensor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Site not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<Sensor> createSensor(@RequestParam String siteName,@RequestParam String code, @RequestParam SensorType type, @RequestParam String model) {
        return ResponseEntity.ok(sensorService.create(siteName,code,type,model));
    }

    @Operation(summary = "Update Sensor Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor Site updated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Sensor not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PatchMapping("/updateSite")
    public ResponseEntity<Sensor> updateSite(@RequestParam String code, @RequestParam String siteName) {
        return ResponseEntity.ok(sensorService.updateSite(code,siteName));
    }

    @Operation(summary = "Delete Sensor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Sensor not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam String code) {
        sensorService.delete(code);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get Sensor Info by Code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor found"),
            @ApiResponse(responseCode = "400", description = "Invalid name parameter", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Sensor not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/findByCode")
    public ResponseEntity<Sensor> getByCode(@RequestParam String code) {
        return ResponseEntity.ok(sensorService.findByCode(code));
    }
}