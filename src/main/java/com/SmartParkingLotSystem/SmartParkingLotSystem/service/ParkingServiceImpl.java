package com.SmartParkingLotSystem.SmartParkingLotSystem.service;

import com.SmartParkingLotSystem.SmartParkingLotSystem.entity.ParkingSpot;
import com.SmartParkingLotSystem.SmartParkingLotSystem.entity.ParkingTransactions;
import com.SmartParkingLotSystem.SmartParkingLotSystem.entity.Vehicle;
import com.SmartParkingLotSystem.SmartParkingLotSystem.exception.VehicleNotFoundException;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.dto.CheckInRequest;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.dto.CheckOutRequest;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.dto.ParkingTransactionDTO;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.PaymentStatus;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.TransactionStatus;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.VehicleType;
import com.SmartParkingLotSystem.SmartParkingLotSystem.repository.ParkingSpotRepository;
import com.SmartParkingLotSystem.SmartParkingLotSystem.repository.ParkingTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ParkingServiceImpl implements ParkingService{
    private final VehicleService vehicleService;
    private final SpotAllocationService spotAllocationService;
    private final FeeCalculationService feeCalculationService;

    private final ParkingTransactionRepository transactionRepository;
    private final ParkingSpotRepository spotRepository;

    @Override
    public ParkingTransactionDTO checkIn(CheckInRequest request) {
        log.info("Check-in initiated for vehicle: {}" , request.getLicensePlate());

        var existingTransaction = transactionRepository.findActiveTransactionByLicensePlate(
                request.getLicensePlate(), TransactionStatus.ACTIVE);

        if(existingTransaction.isPresent()){
            throw new IllegalStateException("Vehicle is already parked . Please check out first.");
        }

        Vehicle vehicle = vehicleService.registerVehicle(
                request.getLicensePlate(),
                request.getVehicleType().name(),
                request.getColour(),
                request.getModel());

        ParkingSpot spot = spotAllocationService.allocateSpot(vehicle.getVehicleType());

        ParkingTransactions transactions = ParkingTransactions.builder().vehicle(vehicle).parkingSpot(spot)
                .checkInTime(LocalDateTime.now()).transactionStatus(TransactionStatus.ACTIVE)
                .paymentStatus(PaymentStatus.PENDING).build();

        spot.setCurrentVehicle(vehicle);
        spot.setOccupiedSince(LocalDateTime.now());
        spotRepository.save(spot);

        ParkingTransactions savedTransaction = transactionRepository.save(transactions);

        log.info("Vehicle {} checked in successfully at spot: {}" , vehicle.getLicensePlate() , spot.getSpotNumber());

        return convertToDTO(savedTransaction);
    }

    @Override
    public ParkingTransactionDTO checkOut(CheckOutRequest request) {
        log.info("Check-out initiated for vehicle: {}", request.getLicensePlate());


        ParkingTransactions transaction = transactionRepository
                .findActiveTransactionByLicensePlate(request.getLicensePlate(), TransactionStatus.ACTIVE)
                .orElseThrow(() -> new VehicleNotFoundException(
                        "No active parking found for vehicle: " + request.getLicensePlate()));


        LocalDateTime checkOutTime = LocalDateTime.now();
        long durationMinutes = ChronoUnit.MINUTES.between(
                transaction.getCheckInTime(), checkOutTime);

        log.info("Vehicle: {}, Duration: {} minutes", request.getLicensePlate(), durationMinutes);


        transaction.setCheckOutTime(checkOutTime);
        transaction.setDurationInMinutes(durationMinutes);


        try {
            BigDecimal totalFee = feeCalculationService.calculateFee(transaction);
            log.info("Fee calculation complete: Base={}, Discount={}, Total={}",
                    transaction.getBaseFee(),
                    transaction.getDiscountAmount(),
                    totalFee);


            if (totalFee == null || transaction.getBaseFee() == null) {
                throw new RuntimeException("Fee calculation failed - fees are null");
            }

        } catch (Exception e) {
            log.error("ERROR in fee calculation for vehicle: {}", request.getLicensePlate(), e);

            transaction.setBaseFee(BigDecimal.ZERO);
            transaction.setDiscountAmount(BigDecimal.ZERO);
            transaction.setTotalFee(BigDecimal.ZERO);
        }


        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        transaction.setPaymentStatus(PaymentStatus.COMPLETED);


        ParkingTransactions savedTransaction = transactionRepository.save(transaction);


        spotAllocationService.releaseSpot(transaction.getParkingSpot().getId());

        log.info("Checkout complete - Vehicle: {}, Fee: {}",
                request.getLicensePlate(), savedTransaction.getTotalFee());

        return convertToDTO(savedTransaction);
    }

    @Override
    public ParkingTransactionDTO getParkingStatus(String licensePlate){
        ParkingTransactions transactions = transactionRepository.findActiveTransactionByLicensePlate(
                licensePlate , TransactionStatus.ACTIVE).orElseThrow(
                () -> new VehicleNotFoundException("Vehicle not currently parked: "+licensePlate));

        return convertToDTO(transactions);
    }

    @Override
    public Long getAvailableSpotsCount(String vehicleTypeStr) {
        VehicleType vehicleType = VehicleType.valueOf(vehicleTypeStr.toUpperCase());

        return spotAllocationService.countAvailableSpots(vehicleType);
    }

    private ParkingTransactionDTO convertToDTO(ParkingTransactions transactions){
        return ParkingTransactionDTO.builder()
                .id(transactions.getId())
                .licensePlate(transactions.getVehicle().getLicensePlate())
                .spotNumber(transactions.getParkingSpot().getSpotNumber())
                .checkInTime(transactions.getCheckInTime())
                .checkOutTime(transactions.getCheckOutTime())
                .durationMinutes(transactions.getDurationInMinutes())
                .baseFee(transactions.getBaseFee())
                .discountAmount(transactions.getDiscountAmount())
                .totalFee(transactions.getTotalFee())
                .transactionStatus(transactions.getTransactionStatus())
                .paymentStatus(transactions.getPaymentStatus())
                .build();
    }
}
