package com.SmartParkingLotSystem.SmartParkingLotSystem.utils;

public class ValidationUtil {
    private static final String LICENSE_PLATE_REGEX = "[A-Z0-9]{2,10}";

    public static boolean isValidLicensePlate(String licensePlate) {
        return licensePlate!=null && licensePlate.matches(LICENSE_PLATE_REGEX);
    }

    public static boolean isValidParkingDuration(long durationMinutes){
        return durationMinutes>0 && durationMinutes <= Constants.MAX_PARKING_DURATION_HOURS * 60;
    }
}
