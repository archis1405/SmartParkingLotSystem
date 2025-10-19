package com.SmartParkingLotSystem.SmartParkingLotSystem.controllers;

import com.SmartParkingLotSystem.SmartParkingLotSystem.models.dto.ApiResponse;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.dto.CheckInRequest;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.dto.CheckOutRequest;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.dto.ParkingTransactionDTO;
import com.SmartParkingLotSystem.SmartParkingLotSystem.service.ParkingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parking")
@RequiredArgsConstructor
@Slf4j
public class ParkingController {
    private final ParkingService parkingService;

    @PostMapping("/check-in")
    public ResponseEntity<ApiResponse<ParkingTransactionDTO>> checkIn(@Valid @RequestBody CheckInRequest request){
        log.info("Received check-in request for vehicle: {}" , request.getLicensePlate());
        ParkingTransactionDTO transaction = parkingService.checkIn(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Vehicle checked in successfully" , transaction));
    }

    @PostMapping("/check-out")
    public ResponseEntity<ApiResponse<ParkingTransactionDTO>> checkOut(@Valid@RequestBody CheckOutRequest request){
        log.info("Recieved check-out request for vehicle: {}" , request.getLicensePlate());
        ParkingTransactionDTO transaction = parkingService.checkOut(request);

        return ResponseEntity.ok(ApiResponse.success("Vehicle checked out successfully" , transaction));
    }

    @GetMapping("/status/{licensePlate}")
    public ResponseEntity<ApiResponse<ParkingTransactionDTO>> getStatus(@PathVariable String licensePlate){
        log.info("Fething status for vehicle: {}" , licensePlate);
        ParkingTransactionDTO transaction = parkingService.getParkingStatus(licensePlate);

        return ResponseEntity.ok(ApiResponse.success("Status fetched successfully" , transaction));
    }

    @GetMapping("/available-spots/{vehicleType}")
    public ResponseEntity<ApiResponse<Long>> getAvailableSpots(@PathVariable String vehicleType){
        log.info("Fetching available spots for vehicle type: {}" , vehicleType);
        Long availableSpots = parkingService.getAvailableSpotsCount(vehicleType);

        return ResponseEntity.ok(ApiResponse.success("Available spots fetched successfully" , availableSpots));
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health(){
        return ResponseEntity.ok(ApiResponse.success("Parking Service is up and running" , "OK"));
    }
}
