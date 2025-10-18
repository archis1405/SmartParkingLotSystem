package com.SmartParkingLotSystem.SmartParkingLotSystem.models.dto;

import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.PaymentStatus;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.TransactionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingTransactionDTO {
    private Long id;
    private String LicensePlate;
    private String spotNumber;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private Long durationMinutes;
    private BigDecimal baseFee;
    private BigDecimal discountAmount;
    private BigDecimal totalFee;
    private TransactionStatus transactionStatus;
    private PaymentStatus paymentStatus;
}
