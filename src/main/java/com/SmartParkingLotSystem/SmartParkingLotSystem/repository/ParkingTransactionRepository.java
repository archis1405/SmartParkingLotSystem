package com.SmartParkingLotSystem.SmartParkingLotSystem.repository;

import com.SmartParkingLotSystem.SmartParkingLotSystem.entity.ParkingTransactions;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingTransactionRepository extends JpaRepository<ParkingTransactions , Long> {

    @Query("SELECT pt FROM ParkingTransactions pt WHERE pt.vehicle.licensePlate = :licensePlate AND pt.transactionStatus = :status ORDER BY pt.checkInTime DESC LIMIT 1")
    Optional<ParkingTransactions> findActiveTransactionByLicensePlate(@Param("licensePlate") String licensePlate , @Param("status")TransactionStatus status);

    @Query("SELECT pt FROM ParkingTransactions pt WHERE pt.checkInTime BETWEEN :startTime AND :endTime")
    List<ParkingTransactions> findTransactionsByDateRange(@Param("startTime")LocalDateTime startTime , @Param("endTime") LocalDateTime endTime);

    @Query("SELECT pt FROM ParkingTransactions pt WHERE pt.vehicle.id = :vehicleId ORDER BY pt.checkInTime DESC")
    List<ParkingTransactions> findTransactionsByVehicle(@Param("vehicleId") Long VehicleId);
}
