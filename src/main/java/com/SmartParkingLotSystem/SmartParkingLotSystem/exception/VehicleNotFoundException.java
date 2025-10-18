package com.SmartParkingLotSystem.SmartParkingLotSystem.exception;

public class VehicleNotFoundException extends ParkingException{
    public VehicleNotFoundException(String message){
        super(message, "VEHICLE_NOT_FOUND");
    }
}
