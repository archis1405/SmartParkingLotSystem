package com.SmartParkingLotSystem.SmartParkingLotSystem.repository;

import com.SmartParkingLotSystem.SmartParkingLotSystem.entity.ParkingFloor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingFloorRepository extends JpaRepository<ParkingFloor , Long> {
}
