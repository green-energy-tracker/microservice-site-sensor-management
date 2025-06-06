package com.green.energy.tracker.site_sensor_management.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DltRecord {
    private String model;
    private String payload;
    private String error;
    private String causedBy;
}
