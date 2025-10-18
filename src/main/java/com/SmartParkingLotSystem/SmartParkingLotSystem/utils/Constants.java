package com.SmartParkingLotSystem.SmartParkingLotSystem.utils;

public class Constants {
    public static final double BASE_HOURLY_RATE = 10.0;

    public static final int MAX_PARKING_DURATION_HOURS = 24;

    public static final int GRACE_PERIOD_MINUTES = 15;

    public static final String INVALID_LICENSE_PLATE_MESSAGE = "INVALID LICENSE PLATE ";
    public static final String INVALID_VELICLE_TYPE = "THE VEHICLE TYPE IS NOT ALLOWED IN PARKING LOT";
    public static final String NO_SPOT_AVAILABLE = "NO SPOT AVAILABLE FOR THE VEHICLE";
    public static final String VEHICLE_NOT_FOUND = "VEHICLE NOT FOUND IN PARKING LOT";

    public static final String CHECKIN_SUCCESSFUL = "VEHICLE CHECKED IN SUCCESSFULLY";
    public static final String CHECKOUT_SUCCESSFUL = "VEHICLE CHECKED OUT SUCCESSFULLY";

    private Constants(){
        throw new AssertionError("Constants cannot be instantiated");
    }
}
