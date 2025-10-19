package com.SmartParkingLotSystem.SmartParkingLotSystem.service;

import com.SmartParkingLotSystem.SmartParkingLotSystem.entity.Vehicle;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.dto.VehicleDTO;

public interface VehicleService {
    Vehicle registerVehicle(String licensePlate , String vehicleType , String colour , String model);
    Vehicle getVehicleByLicensePlate(String licensePlate);
    VehicleDTO convertToDTO(Vehicle vehicle);
}
