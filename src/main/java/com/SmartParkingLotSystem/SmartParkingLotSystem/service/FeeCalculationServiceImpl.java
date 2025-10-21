package com.SmartParkingLotSystem.SmartParkingLotSystem.service;

import com.SmartParkingLotSystem.SmartParkingLotSystem.entity.FeeStructure;
import com.SmartParkingLotSystem.SmartParkingLotSystem.entity.ParkingTransactions;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.VehicleType;
import com.SmartParkingLotSystem.SmartParkingLotSystem.repository.FeeStructureRepository;
import com.SmartParkingLotSystem.SmartParkingLotSystem.utils.Constants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FeeCalculationServiceImpl implements FeeCalculationService{
    private final FeeStructureRepository feeStructureRepository;

    @Override
    public BigDecimal calculateFee(ParkingTransactions transactions) {
        log.info("Starting fee calculation for transaction: {}", transactions.getId());

        // Validate duration
        if (transactions.getDurationInMinutes() == null || transactions.getDurationInMinutes() <= 0) {
            log.warn("Duration is null or zero, setting fee to 0");
            transactions.setBaseFee(BigDecimal.ZERO);
            transactions.setDiscountAmount(BigDecimal.ZERO);
            transactions.setTotalFee(BigDecimal.ZERO);
            return BigDecimal.ZERO;
        }

        log.info("Duration: {} minutes", transactions.getDurationInMinutes());

        // Get active fee structure OR create default
        FeeStructure feeStructure = feeStructureRepository
                .findFirstByIsActiveTrueOrderByCreatedAtDesc()
                .orElseGet(() -> {
                    log.warn("No active fee structure found, creating default");
                    return createDefaultFeeStructure();
                });

        log.info("Using fee structure: {} with hourly rate: {}",
                feeStructure.getName(), feeStructure.getHourlyRate());

        // Calculate base fee
        double durationHours = transactions.getDurationInMinutes() / 60.0;
        double vehicleMultiplier = transactions.getVehicle().getVehicleType().getFeeMultiplier();

        log.info("Calculation: {} hours × ${} × {} multiplier",
                durationHours, feeStructure.getHourlyRate(), vehicleMultiplier);

        BigDecimal baseFee = feeStructure.getHourlyRate()
                .multiply(BigDecimal.valueOf(durationHours))
                .multiply(BigDecimal.valueOf(vehicleMultiplier))
                .setScale(2, RoundingMode.HALF_UP);

        log.info("Base fee calculated: {}", baseFee);
        transactions.setBaseFee(baseFee);

        // Apply discount if eligible
        BigDecimal discount = applyDiscount(baseFee, transactions.getDurationInMinutes());
        log.info("Discount applied: {}", discount);
        transactions.setDiscountAmount(discount);

        // Calculate total fee
        BigDecimal totalFee = baseFee.subtract(discount);

        // Apply daily cap if configured
        if (feeStructure.getDailyCap() != null && totalFee.compareTo(feeStructure.getDailyCap()) > 0) {
            log.info("Daily cap applied: {} -> {}", totalFee, feeStructure.getDailyCap());
            totalFee = feeStructure.getDailyCap();
        }

        transactions.setTotalFee(totalFee);

        log.info("Final fee - Base: {}, Discount: {}, Total: {}",
                baseFee, discount, totalFee);

        return totalFee;
    }

    @Override
    public BigDecimal calculateEstimatedFee(Long durationMinutes, String vehicleTypeStr) {
        if (durationMinutes == null || durationMinutes <= 0) {
            return BigDecimal.ZERO;
        }

        VehicleType vehicleType = VehicleType.valueOf(vehicleTypeStr);
        FeeStructure feeStructure = feeStructureRepository.findFirstByIsActiveTrueOrderByCreatedAtDesc()
                .orElse(createDefaultFeeStructure());

        double durationHours = durationMinutes / 60.0;
        double vehicleMultiplier = vehicleType.getFeeMultiplier();

        BigDecimal estimatedFee = feeStructure.getHourlyRate()
                .multiply(BigDecimal.valueOf(durationHours))
                .multiply(BigDecimal.valueOf(vehicleMultiplier))
                .setScale(2, RoundingMode.HALF_UP);

        return estimatedFee;
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal baseFee, Long durationMinutes) {
        FeeStructure feeStructure = feeStructureRepository.findFirstByIsActiveTrueOrderByCreatedAtDesc()
                .orElse(createDefaultFeeStructure());


        if (feeStructure.getDiscountPercentage() == null ||
                feeStructure.getMinDurationMinutes() == null) {
            return BigDecimal.ZERO;
        }

        if (durationMinutes >= feeStructure.getMinDurationMinutes()) {
            BigDecimal discountPercentage = BigDecimal.valueOf(feeStructure.getDiscountPercentage());
            BigDecimal discount = baseFee
                    .multiply(discountPercentage)
                    .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                    .setScale(2, RoundingMode.HALF_UP);

            log.info("Applied discount of {}% on fee: {}", feeStructure.getDiscountPercentage(), discount);
            return discount;
        }
        return BigDecimal.ZERO;
    }

    private FeeStructure createDefaultFeeStructure() {
        return FeeStructure.builder()
                .name("Default")
                .hourlyRate(BigDecimal.valueOf(Constants.BASE_HOURLY_RATE))
                .discountPercentage(10.0)
                .minDurationMinutes(180) // 3 hours
                .isActive(true)
                .build();
    }
}
