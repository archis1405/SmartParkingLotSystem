package com.SmartParkingLotSystem.SmartParkingLotSystem.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public interface ReportService {
    HashMap<String , Object> getRevenueReport(LocalDateTime startDate , LocalDateTime endDate);
    HashMap<String , Object> getOccupancyReport();
    List<HashMap<String , Object>> getVehicleActivityReport(LocalDateTime startDate , LocalDateTime endDate);
}
