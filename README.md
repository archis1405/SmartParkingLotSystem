# Smart Parking Lot System - Complete Implementation Guide

## System Overview

A production-ready Spring Boot backend system for comprehensive smart parking lot management with real-time vehicle tracking, intelligent spot allocation, and dynamic fee calculation.

---

## Low-Level Architecture

### System Components

```
┌─────────────────────────────────────────────────────────────────┐
│                          REST API Layer                          │
│  (ParkingController, AdminController, ReportController)          │
└────────────────────┬────────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────────┐
│                    Service Layer                                 │
│  ┌──────────────────┬──────────────────┬──────────────────┐    │
│  │  ParkingService  │  SpotAllocation  │  FeeCalculation  │    │
│  │                  │     Service      │     Service      │    │
│  └──────────────────┴──────────────────┴──────────────────┘    │
│  ┌──────────────────┬──────────────────┐                      │
│  │  VehicleService  │  ReportService   │                      │
│  └──────────────────┴──────────────────┘                      │
└────────────────────┬────────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────────┐
│                  Repository Layer (JPA)                          │
│  ┌──────────┬──────────┬──────────┬──────────┬──────────┐       │
│  │ParkingLot│  Floor   │  Spot    │ Vehicle  │Transaction│      │
│  │Repo      │  Repo    │  Repo    │  Repo    │   Repo    │      │
│  └──────────┴──────────┴──────────┴──────────┴──────────┘       │
└────────────────────┬────────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────────┐
│                    Database Layer (MySQL)                        │
│  ┌──────────┬──────────┬──────────┬──────────┬──────────┐       │
│  │Parking   │  Parking │Parking   │ Vehicle  │Parking   │       │
│  │  Lots    │  Floors  │  Spots   │  Tables  │Transact. │       │
│  └──────────┴──────────┴──────────┴──────────┴──────────┘       │
└─────────────────────────────────────────────────────────────────┘
```

---

## Data Flow Diagrams

### Check-In Flow

```
Client Request: POST /api/v1/parking/check-in
     │
     ▼
┌─────────────────────┐
│ ParkingController   │ ✓ Validates request format
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ VehicleService      │ ✓ Register/retrieve vehicle
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ SpotAllocation      │ ✓ Find available spot
│ Service             │   (First-Fit algorithm)
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ Transaction Create  │ ✓ Create parking record
│ & Persist           │ ✓ Update spot status
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ Response: 201       │ ✓ Return transaction details
│ Created             │
└─────────────────────┘
```

### Check-Out Flow

```
Client Request: POST /api/v1/parking/check-out
     │
     ▼
┌─────────────────────┐
│ ParkingController   │ ✓ Validate license plate
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ Find Active         │ ✓ Query for ongoing session
│ Transaction         │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ FeeCalculation      │ ✓ Duration calculation
│ Service             │ ✓ Base fee computation
│                     │ ✓ Apply discounts
│                     │ ✓ Apply caps
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ SpotAllocation      │ ✓ Release spot
│ Service             │ ✓ Mark as available
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ Update Transaction  │ ✓ Set status COMPLETED
│ Complete & Persist  │ ✓ Mark PAID
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ Response: 200 OK    │ ✓ Return transaction summary
│                     │   with fees
└─────────────────────┘
```

---

## Project Structure at a Glance

```
src/main/java/com/smartparking/
├── ParkingLotApplication.java          ← Start here
├── controller/                          ← REST endpoints
│   ├── ParkingController.java
│   ├── AdminController.java
│   └── ReportController.java
├── service/                             ← Business logic
│   ├── ParkingService.java
│   ├── SpotAllocationService.java
│   ├── FeeCalculationService.java
│   └── impl/                            ← Implementation
├── entity/                              ← Database models
│   ├── Vehicle.java
│   ├── ParkingSpot.java
│   └── ParkingTransaction.java
├── repository/                          ← Data access
│   ├── VehicleRepository.java
│   ├── ParkingSpotRepository.java
│   └── ParkingTransactionRepository.java
├── model/
│   ├── dto/                             ← API contracts
│   │   ├── CheckInRequest.java
│   │   └── CheckOutRequest.java
│   └── enums/                           ← Enumerations
│       ├── VehicleType.java
│       └── ParkingSpotStatus.java
├── exception/                           ← Error handling
│   ├── ParkingException.java
│   └── GlobalExceptionHandler.java
└── util/                                ← Utilities
    ├── Constants.java
    └── ValidationUtil.java
```
