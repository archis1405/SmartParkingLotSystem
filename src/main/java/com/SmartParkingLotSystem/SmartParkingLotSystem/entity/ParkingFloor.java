package com.SmartParkingLotSystem.SmartParkingLotSystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;

@Entity
@Table(name="parking_floor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingFloor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parking_lot_id", nullable = false)
    private ParkingLot parkingLot;

    @Column(name="floor_number", nullable = false)
    private Integer floorNumber;

    @Column(name="total_spots", nullable = false)
    private Integer totalSpots;

    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ParkingSpot> spots;

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
