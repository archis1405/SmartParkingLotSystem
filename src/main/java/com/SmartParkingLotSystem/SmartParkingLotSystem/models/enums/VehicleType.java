package com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums;

public enum VehicleType {

    // VehicleType(Spot size , fees multiplier for the parking spot
    MOTORCYCLE(1,1.0),
    CAR(2,1.5),
    SUV(3,2.0),
    BUS(4,3.0);

    private final int spotsRequired;
    private final double feeMultiplier;

    VehicleType(int spotsRequired , double feeMultiplier){
        this.spotsRequired = spotsRequired;
        this.feeMultiplier = feeMultiplier;
    }

    public int getSpotsRequired() {
        return spotsRequired;
    }

    public double getFeeMultiplier() {
        return feeMultiplier;
    }
}
