package com.SmartParkingLotSystem.SmartParkingLotSystem.models.dto;

import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleDTO {
    private Long id;
    private String licensePlate;
    private VehicleType vehicleType;
    private String colour;
    private String model;
}
