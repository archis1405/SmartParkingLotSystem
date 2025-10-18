package com.SmartParkingLotSystem.SmartParkingLotSystem.models.dto;

import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.VehicleType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckInRequest {
    @NotBlank(message = "License plate is required")
    private String licensePlate;

    @NotNull(message = "Vehicle type is required")
    private String vehicleType;

    private String colour;

    private String model;
}
