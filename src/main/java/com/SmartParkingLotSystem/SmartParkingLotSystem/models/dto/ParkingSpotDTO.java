package com.SmartParkingLotSystem.SmartParkingLotSystem.models.dto;

import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.ParkingSpotStatus;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingSpotDTO {
    private Long id;
    private String spotNumber;
    private Integer floorNumber;
    private ParkingSpotStatus status;
    private String occupiedByVehicle;
}
