package com.SmartParkingLotSystem.SmartParkingLotSystem.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    @PostMapping("/initialize-lot")
    public ResponseEntity<String> initializeLot(){
        log.info("Initializing parking lot");
        return ResponseEntity.ok("Parking lot initialized successfully");
    }

    public ResponseEntity<String> getSystemStatus(){
        return ResponseEntity.ok("System is operational");
    }
}
