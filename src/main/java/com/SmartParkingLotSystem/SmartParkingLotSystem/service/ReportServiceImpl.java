package com.SmartParkingLotSystem.SmartParkingLotSystem.service;

import com.SmartParkingLotSystem.SmartParkingLotSystem.entity.ParkingSpot;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.ParkingSpotStatus;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.TransactionStatus;
import com.SmartParkingLotSystem.SmartParkingLotSystem.repository.ParkingSpotRepository;
import com.SmartParkingLotSystem.SmartParkingLotSystem.repository.ParkingTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReportServiceImpl implements ReportService{

    private final ParkingTransactionRepository transactionRepository;
    private final ParkingSpotRepository spotRepository;

    @Override
    public HashMap<String, Object> getRevenueReport(LocalDateTime startDate, LocalDateTime endDate) {
        var transactions = transactionRepository.findTransactionsByDateRange(startDate,endDate);

        BigDecimal totalRevenue = transactions.stream().filter(t -> t.getTransactionStatus() == TransactionStatus.COMPLETED).
                map(t -> t.getTotalFee() != null ? t.getTotalFee() : BigDecimal.ZERO).
                reduce(BigDecimal.ZERO , BigDecimal::add);

        HashMap<String , Object> report = new HashMap<>();
        report.put("startDate" , startDate);
        report.put("endDate" , endDate);
        report.put("totalRevenue" , totalRevenue);
        report.put("completedTransactions" , transactions.stream()
                .filter(t -> t.getTransactionStatus() == TransactionStatus.COMPLETED).count());
        report.put("averageFee" , transactions.isEmpty() ? 0 : totalRevenue.divide(BigDecimal.valueOf(transactions.size()),2, RoundingMode.HALF_UP));

        return report;
    }

    @Override
    public HashMap<String, Object> getOccupancyReport() {
        List<ParkingSpot> allSpots = spotRepository.findAll();

        long occupiedSpots = allSpots.stream().filter(s -> s.getStatus() == ParkingSpotStatus.OCCUPIED).count();

        long availableSpots = allSpots.stream().filter(s -> s.getStatus() == ParkingSpotStatus.AVAILABLE).count();

        HashMap<String , Object> stats = new HashMap<>();
        stats.put("totalSpots" , allSpots.size());
        stats.put("occupiedSpots" , occupiedSpots);
        stats.put("availableSpots" , availableSpots);
        stats.put("occupancyPercentage" , allSpots.isEmpty() ? 0 : (occupiedSpots*100.0)/allSpots.size());

        return stats;
    }

    @Override
    public List<HashMap<String, Object>> getVehicleActivityReport(LocalDateTime startDate, LocalDateTime endDate) {
        var transactions = transactionRepository.findTransactionsByDateRange(startDate,endDate);
        List<HashMap<String,Object>> report = new ArrayList<>();

        transactions.forEach(t -> {
            HashMap<String,Object> entry = new HashMap<>();
            entry.put("licensePlate" , t.getVehicle().getLicensePlate());
            entry.put("vehicleType" , t.getVehicle().getVehicleType());
            entry.put("checkInTime" , t.getCheckInTime());
            entry.put("checkOutTime" , t.getCheckOutTime());
            entry.put("durationMinutes" , t.getDurationInMinutes());
            entry.put("totalFee" , t.getTotalFee());
            report.add(entry);
        });

        return report;
    }
}
