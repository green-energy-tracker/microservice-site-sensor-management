package com.green.energy.tracker.site_sensor_management.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@Table(name = "sensors")
@AllArgsConstructor
@NoArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "model", nullable = false)
    private String model;
    @Column(name = "status", nullable = false)
    private String status;
}
