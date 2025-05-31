package com.green.energy.tracker.site_sensor_management.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Data
@Builder
@Entity
@Table(name = "sites")
@AllArgsConstructor
@NoArgsConstructor
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "location", nullable = false)
    private String location;
    @Column(name = "ownerId", nullable = false)
    private Long ownerId;
    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sensor> sensors = new ArrayList<>();
}