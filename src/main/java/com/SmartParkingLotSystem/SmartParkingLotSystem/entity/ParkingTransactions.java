package com.SmartParkingLotSystem.SmartParkingLotSystem.entity;

import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.PaymentStatus;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="parking_transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parking_spot_id", nullable = false)
    private ParkingSpot parkingSpot;

    @Column(name="check_in_time", nullable = false)
    private LocalDateTime checkInTime;

    @Column(name="check_out_time",nullable = false)
    private LocalDateTime checkOutTime;

    @Column(name="duration_in_minutes", nullable = false)
    private Long durationInMinutes;

    @Column(name = "base_fee", nullable = false , precision = 10 , scale = 2)
    private BigDecimal baseFee;

    @Column(name = "discount_amount", nullable = false , precision = 10 , scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "total_fee", nullable = false , precision = 10 , scale = 2)
    private BigDecimal totalFee;

    @Enumerated(EnumType.STRING)
    @Column(name="transaction_status", nullable = false)
    private TransactionStatus transactionStatus;

    @Enumerated(EnumType.STRING)
    @Column(name="payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable = false)
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
