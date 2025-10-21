# Postman - Complete API Requests & Body

## BASE URL
```
http://localhost:8080/parking-lot/api/v1
```

---

## TEST 1: HEALTH CHECK

### Request
```
Method: GET
URL: http://localhost:8080/parking-lot/api/v1/parking/health
Headers: None
Body: None
```

---

---

##  TEST 2: CHECK-IN - CAR

### Request
```
Method: POST
URL: http://localhost:8080/parking-lot/api/v1/parking/check-in
Headers: Content-Type: application/json
```

### Body
```json
{
  "licensePlate": "ABC1234",
  "vehicleType": "CAR",
  "color": "Red",
  "model": "Honda Civic"
}
```
---

## 📋 TEST 3: CHECK-IN - MOTORCYCLE

### Request
```
Method: POST
URL: http://localhost:8080/parking-lot/api/v1/parking/check-in
Headers: Content-Type: application/json
```

### Body
```json
{
  "licensePlate": "BIKE5678",
  "vehicleType": "MOTORCYCLE",
  "color": "Black",
  "model": "Harley Davidson"
}
```
---

## 📋 TEST 4: CHECK-IN - SUV

### Request
```
Method: POST
URL: http://localhost:8080/parking-lot/api/v1/parking/check-in
Headers: Content-Type: application/json
```

### Body
```json
{
  "licensePlate": "SUV9012",
  "vehicleType": "SUV",
  "color": "Silver",
  "model": "Toyota Fortuner"
}
```
---

## 📋 TEST 5: CHECK-IN - BUS

### Request
```
Method: POST
URL: http://localhost:8080/parking-lot/api/v1/parking/check-in
Headers: Content-Type: application/json
```

### Body
```json
{
  "licensePlate": "BUS3456",
  "vehicleType": "BUS",
  "color": "Yellow",
  "model": "School Bus"
}
```
---

## TEST 6: GET PARKING STATUS

### Request
```
Method: GET
URL: http://localhost:8080/parking-lot/api/v1/parking/status/ABC1234
Headers: None
Body: None
```
---

## TEST 7: GET PARKING STATUS - NON-EXISTENT

### Request
```
Method: GET
URL: http://localhost:8080/parking-lot/api/v1/parking/status/DOESNOTEXIST
Headers: None
Body: None
```
---

## 📋 TEST 8: CHECK-OUT - VALID VEHICLE

### Request
```
Method: POST
URL: http://localhost:8080/parking-lot/api/v1/parking/check-out
Headers: Content-Type: application/json
```

### Body
```json
{
  "licensePlate": "ABC1234"
}
```
---

## 📋 TEST 9: CHECK-OUT - VEHICLE NOT PARKED

### Request
```
Method: POST
URL: http://localhost:8080/parking-lot/api/v1/parking/check-out
Headers: Content-Type: application/json
```

### Body
```json
{
  "licensePlate": "NOTPARKED"
}
```
---

## TEST 10: AVAILABLE SPOTS - CAR

### Request
```
Method: GET
URL: http://localhost:8080/parking-lot/api/v1/parking/available-spots/CAR
Headers: None
Body: None
```
---

## 📋 TEST 11: AVAILABLE SPOTS - MOTORCYCLE

### Request
```
Method: GET
URL: http://localhost:8080/parking-lot/api/v1/parking/available-spots/MOTORCYCLE
Headers: None
Body: None
```
---

## 📋 TEST 12: AVAILABLE SPOTS - SUV

### Request
```
Method: GET
URL: http://localhost:8080/parking-lot/api/v1/parking/available-spots/SUV
Headers: None
Body: None
```
---

## 📋 TEST 13: AVAILABLE SPOTS - BUS

### Request
```
Method: GET
URL: http://localhost:8080/parking-lot/api/v1/parking/available-spots/BUS
Headers: None
Body: None
```
---

## 📋 TEST 14: AVAILABLE SPOTS - INVALID TYPE

### Request
```
Method: GET
URL: http://localhost:8080/parking-lot/api/v1/parking/available-spots/TRUCK
Headers: None
Body: None
```
---

## 📋 TEST 15: OCCUPANCY REPORT

### Request
```
Method: GET
URL: http://localhost:8080/parking-lot/api/v1/reports/occupancy
Headers: None
Body: None
```
---

## 📋 TEST 16: REVENUE REPORT - TODAY

### Request
```
Method: GET
URL: http://localhost:8080/parking-lot/api/v1/reports/revenue?startDate=2025-10-18T00:00:00&endDate=2025-10-18T23:59:59
Headers: None
Body: None
```

**Note**: Change dates to TODAY's date in format: YYYY-MM-DDTHH:MM:SS
---

## 📋 TEST 17: REVENUE REPORT - THIS MONTH

### Request
```
Method: GET
URL: http://localhost:8080/parking-lot/api/v1/reports/revenue?startDate=2025-10-01T00:00:00&endDate=2025-10-31T23:59:59
Headers: None
Body: None
```
---

## 📋 TEST 18: ACTIVITY REPORT - THIS MONTH

### Request
```
Method: GET
URL: http://localhost:8080/parking-lot/api/v1/reports/activity?startDate=2025-10-01T00:00:00&endDate=2025-10-31T23:59:59
Headers: None
Body: None
```
