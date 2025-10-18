package com.SmartParkingLotSystem.SmartParkingLotSystem.repository;

import com.SmartParkingLotSystem.SmartParkingLotSystem.entity.ParkingSpot;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.ParkingSpotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.*;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot,Long> {

    Optional<ParkingSpot> findBySpotNumber(String spotNumber);

    @Query("SELECT ps FROM ParkingSpot ps WHERE ps.floor.id = :floorId AND ps.status = :status ORDER BY ps.id ASC")
    List<ParkingSpot> findAvailableSpotsByFloor(@Param("floorId") Long floorId , @Param("status") ParkingSpotStatus status);

    @Query("SELECT COUNT(ps) FROM ParkingSpot ps WHERE ps.floor.id = :floorId AND ps.status = :status")
    Long countAvailableSpots(@Param("floorId") Long floorId , @Param("status") ParkingSpotStatus status);

    @Query("SELECT ps FROM ParkingSpot ps WHERE ps.currentVehicle.id = :vehicleId")
    Optional<ParkingSpot> findSpotByCurrentVehicle(@Param("vehicleId") Long vehicleId);
}
