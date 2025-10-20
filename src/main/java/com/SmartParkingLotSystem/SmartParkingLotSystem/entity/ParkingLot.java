package com.SmartParkingLotSystem.SmartParkingLotSystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "parking_lot" , indexes = {
        @Index(name = "idx_lot_name" , columnList = "lot_name")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="lot_name" , nullable = false , unique = true)
    private String lotName;

    @Column(name ="location" , nullable = false)
    private String location;

    @Column(name = "total_floors" , nullable = false)
    private Integer totalFloors;

    @Column(name = "total_spots" , nullable = false)
    private Integer totalSpots;

    @OneToMany(mappedBy = "parkingLot" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<ParkingFloor> floors;

    @Column(name = "created_at" , nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        createdAt=LocalDateTime.now();
        updatedAt=LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt=LocalDateTime.now();
    }
}
