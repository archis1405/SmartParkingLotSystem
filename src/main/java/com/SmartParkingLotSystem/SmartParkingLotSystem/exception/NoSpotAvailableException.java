package com.SmartParkingLotSystem.SmartParkingLotSystem.exception;

public class NoSpotAvailableException extends ParkingException{
    public NoSpotAvailableException(String message){
        super(message, "NO_SPOT_AVAILABLE");
    }
}
