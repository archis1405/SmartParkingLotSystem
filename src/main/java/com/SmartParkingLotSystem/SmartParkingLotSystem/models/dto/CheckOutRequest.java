package com.SmartParkingLotSystem.SmartParkingLotSystem.models.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckOutRequest {
    @NotBlank(message = "License plate is required")
    private String licensePlate;
}
