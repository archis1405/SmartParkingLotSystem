package com.SmartParkingLotSystem.SmartParkingLotSystem.controllers;

import com.SmartParkingLotSystem.SmartParkingLotSystem.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Slf4j
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/revenue")
    public ResponseEntity<HashMap<String , Object>> getRevenueReport(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime startDate,
                                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime endDate){
        log.info("Generating revenue report from {} to {}" , startDate , endDate);
        HashMap<String , Object> report = reportService.getRevenueReport(startDate, endDate);

        return ResponseEntity.ok(report);
    }

    @GetMapping("/activity")
    public ResponseEntity<List<HashMap<String , Object>>> getActivityReport(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime startDate,
                                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime endDate){
        log.info("Generating vehicle activity report from {} to {}" , startDate , endDate);
        List<HashMap<String , Object>> report = reportService.getVehicleActivityReport(startDate, endDate);

        return ResponseEntity.ok(report);
    }
    @GetMapping("/occupancy")
    public ResponseEntity<HashMap<String , Object>> getOccupancyReport(){
        log.info("Generating occupancy report");
        HashMap<String , Object> stats = reportService.getOccupancyReport();

        return ResponseEntity.ok(stats);
    }
}
