package com.green.energy.tracker.site_sensor_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "code", nullable = false, unique = true )
    private String code;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "site_id", nullable = false)
    @JsonIgnore
    private Site site;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SensorType type;

    @Column(name = "model", nullable = false)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SensorStatus status;
}
