package com.SmartParkingLotSystem.SmartParkingLotSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.SmartParkingLotSystem.SmartParkingLotSystem.entity.ParkingLot;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository extends JpaRepository {
    ParkingLot findByLotName(String lotName);
}
