package com.SmartParkingLotSystem.SmartParkingLotSystem.service;

import com.SmartParkingLotSystem.SmartParkingLotSystem.models.dto.CheckInRequest;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.dto.CheckOutRequest;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.dto.ParkingTransactionDTO;

public interface ParkingService {
    ParkingTransactionDTO checkIn(CheckInRequest request);
    ParkingTransactionDTO checkOut(CheckOutRequest request);
    ParkingTransactionDTO getParkingStatus(String licensePlate);
    Long getAvailableSpotsCount(String vehicleType);
}
