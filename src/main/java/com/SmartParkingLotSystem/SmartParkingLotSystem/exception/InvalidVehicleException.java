package com.SmartParkingLotSystem.SmartParkingLotSystem.exception;

public class InvalidVehicleException extends ParkingException{
    public InvalidVehicleException(String message){
        super(message, "INVALID_VEHICLE");
    }
}
