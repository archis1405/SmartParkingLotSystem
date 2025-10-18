package com.SmartParkingLotSystem.SmartParkingLotSystem.service;

import com.SmartParkingLotSystem.SmartParkingLotSystem.entity.Vehicle;

public interface VehicleService {
    Vehicle registerVehicle(String licensePlate , String vehicleType , String colour , String model);
    Vehicle getVehicleByLicensePlate(String licensePlate);
    Vehicle convertToDTO(Vehicle vehicle);
}
