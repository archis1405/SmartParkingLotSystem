package com.SmartParkingLotSystem.SmartParkingLotSystem.service;

import com.SmartParkingLotSystem.SmartParkingLotSystem.entity.Vehicle;
import com.SmartParkingLotSystem.SmartParkingLotSystem.exception.InvalidVehicleException;
import com.SmartParkingLotSystem.SmartParkingLotSystem.exception.VehicleNotFoundException;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.dto.VehicleDTO;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.VehicleType;
import com.SmartParkingLotSystem.SmartParkingLotSystem.repository.VehicleRepository;
import com.SmartParkingLotSystem.SmartParkingLotSystem.utils.ValidationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VehicleServiceImpl implements VehicleService{
    private final VehicleRepository vehicleRepository;

    @Override
    public Vehicle registerVehicle(String licensePlate, String vehicleTypeStr, String colour, String model) {
        if(!ValidationUtil.isValidLicensePlate(licensePlate)){
            throw new InvalidVehicleException("Invalid license plate");
        }

        VehicleType vehicleType;
        try{
            vehicleType = VehicleType.valueOf(vehicleTypeStr.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new InvalidVehicleException("Invalid vehicle type");
        }

        return vehicleRepository.findByLicensePlate(licensePlate).orElseGet( () -> {
            Vehicle newVehicle = Vehicle.builder().licensePlate(licensePlate).vehicleType(vehicleType).
                    colour(colour).model(model).build();

            Vehicle saved = vehicleRepository.save(newVehicle);
            log.info("Registered new vehicle with license plate: {}" , licensePlate);
            return saved;
        });
    }

    @Override
    public Vehicle getVehicleByLicensePlate(String licensePlate) {
        return vehicleRepository.findByLicensePlate(licensePlate).orElseThrow( () ->
                new VehicleNotFoundException("Vehicle not Fount: "+licensePlate));
    }

    @Override
    public VehicleDTO convertToDTO(Vehicle vehicle) {
        return VehicleDTO.builder().id(vehicle.getId()).licensePlate(vehicle.getLicensePlate()).
                vehicleType(vehicle.getVehicleType()).colour(vehicle.getColour()).
                model(vehicle.getModel()).build();
    }
}
