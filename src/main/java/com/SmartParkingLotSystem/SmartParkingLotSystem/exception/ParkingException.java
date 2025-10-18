package com.SmartParkingLotSystem.SmartParkingLotSystem.exception;

public class ParkingException extends RuntimeException{
    private String errorCode;

    public ParkingException(String message){
        super(message);
        this.errorCode="PARKING_ERROR";
    }

    public ParkingException(String message, String errorCode){
        super(message);
        this.errorCode=errorCode;
    }

    public String getErrorCode(){
        return this.errorCode;
    }
}
