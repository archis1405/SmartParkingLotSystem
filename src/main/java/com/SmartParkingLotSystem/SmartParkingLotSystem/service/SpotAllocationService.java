package com.SmartParkingLotSystem.SmartParkingLotSystem.service;

import com.SmartParkingLotSystem.SmartParkingLotSystem.entity.ParkingSpot;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.VehicleType;

public interface SpotAllocationService {

    ParkingSpot allocateSpot(VehicleType vehicleType);
    void releaseSpot(Long spotId);
    Long countAvailableSpots(VehicleType vehicleType);

}
