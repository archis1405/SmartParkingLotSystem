package com.SmartParkingLotSystem.SmartParkingLotSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;

@Entity
@Table(name="fee_structure")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeStructure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="hourly_rate", nullable = false , precision = 10 , scale = 2)
    private BigDecimal hourlyRate;

    @Column(name="daily_cap", precision = 10 , scale = 2)
    private BigDecimal dailyCap;

    @Column(name="discount_percentage")
    private Double discountPercentage;

    @Column(name="min_duration_minutes")
    private Integer minDurationMinutes;

    @Column(name="is_active")
    private Boolean isActive;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if(isActive==null){
            isActive=true;
        }
    }
}
