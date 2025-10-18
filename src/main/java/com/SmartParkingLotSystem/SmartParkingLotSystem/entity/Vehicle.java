package com.SmartParkingLotSystem.SmartParkingLotSystem.entity;

import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "vehicles" , uniqueConstraints = @UniqueConstraint(columnNames = "license_plate"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_plate" , nullable = false , unique = true)
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type" , nullable = false)
    private VehicleType vehicleType;

    @Column(name = "colour")
    private String colour;

    @Column(name = "model" )
    private String model;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ParkingTransactions> transactions;

    @Column(name = "created_at" , nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at" , nullable = false)
    private LocalDateTime updatedAt;

    protected void onCreate(){
        createdAt=LocalDateTime.now();
        updatedAt=LocalDateTime.now();
    }

    protected void onUpdate(){
        updatedAt=LocalDateTime.now();
    }
}
