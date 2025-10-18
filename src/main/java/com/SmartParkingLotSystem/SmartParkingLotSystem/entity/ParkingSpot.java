package com.SmartParkingLotSystem.SmartParkingLotSystem.entity;

import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.ParkingSpotStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="parking_spots")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name="floor_id", nullable = false)
    private ParkingFloor floor;

    @Column(name="spot_number", nullable = false)
    private String spotNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ParkingSpotStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="vehicle_id")
    private Vehicle currentVehicle;

    @Column(name="occupied_since")
    private LocalDateTime occupiedSince;

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        createdAt=LocalDateTime.now();
        updatedAt=LocalDateTime.now();
        if(status==null){
            status=ParkingSpotStatus.AVAILABLE;
        }
    }

    protected void onUpdate(){
        updatedAt=LocalDateTime.now();
    }
}
