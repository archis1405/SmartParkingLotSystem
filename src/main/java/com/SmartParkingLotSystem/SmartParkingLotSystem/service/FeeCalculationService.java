package com.SmartParkingLotSystem.SmartParkingLotSystem.service;

import com.SmartParkingLotSystem.SmartParkingLotSystem.entity.ParkingTransactions;

import java.math.BigDecimal;

public interface FeeCalculationService {
    BigDecimal calculateFee(ParkingTransactions transactions);
    BigDecimal calculateEstimatedFee(Long durationMinutes , String vehicleType);
    BigDecimal applyDiscount(BigDecimal baseFee, String discountMinutes);
}
