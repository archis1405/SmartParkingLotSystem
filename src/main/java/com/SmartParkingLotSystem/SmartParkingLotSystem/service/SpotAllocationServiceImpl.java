package com.SmartParkingLotSystem.SmartParkingLotSystem.service;

import com.SmartParkingLotSystem.SmartParkingLotSystem.entity.ParkingFloor;
import com.SmartParkingLotSystem.SmartParkingLotSystem.entity.ParkingLot;
import com.SmartParkingLotSystem.SmartParkingLotSystem.entity.ParkingSpot;
import com.SmartParkingLotSystem.SmartParkingLotSystem.exception.NoSpotAvailableException;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.ParkingSpotStatus;
import com.SmartParkingLotSystem.SmartParkingLotSystem.models.enums.VehicleType;
import com.SmartParkingLotSystem.SmartParkingLotSystem.repository.ParkingFloorRepository;
import com.SmartParkingLotSystem.SmartParkingLotSystem.repository.ParkingLotRepository;
import com.SmartParkingLotSystem.SmartParkingLotSystem.repository.ParkingSpotRepository;
import com.SmartParkingLotSystem.SmartParkingLotSystem.service.SpotAllocationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SpotAllocationServiceImpl implements SpotAllocationService{

    private final ParkingLotRepository parkingLotRepository;
    private final ParkingFloorRepository floorRepository;
    private final ParkingSpotRepository spotRepository;

    @Override
    public ParkingSpot allocateSpot(VehicleType vehicleType) {
        log.info("Allocating spot for vehicle type: {}" , vehicleType);

        List<ParkingLot> lots = parkingLotRepository.findAll();
        if(lots.isEmpty()){
            throw new NoSpotAvailableException("No parking lot configured in system");
        }

        ParkingLot lot = lots.get(0);
        List<ParkingFloor> floors = floorRepository.findByParkingLotId(lot.getId());

        for(ParkingFloor floor : floors){
            List<ParkingSpot> availableSpots = spotRepository.findAvailableSpotsByFloor(floor.getId() , ParkingSpotStatus.AVAILABLE);

            for(int i=0 ; i<= availableSpots.size()-vehicleType.getSpotsRequired() ; i++){
                boolean canAllocate = true;

                for(int j=i ; j<i+vehicleType.getSpotsRequired() ; j++){
                    if(!availableSpots.get(j).getStatus().equals(ParkingSpotStatus.AVAILABLE)){
                        canAllocate = false;
                        break;
                    }
                }

                if(canAllocate){
                    ParkingSpot primarySpot = availableSpots.get(i);
                    primarySpot.setStatus(ParkingSpotStatus.OCCUPIED);
                    ParkingSpot savedSpot = spotRepository.save(primarySpot);

                    log.info("Successfully allocated spot: {} for vehicle type : {}" , primarySpot.getSpotNumber() , vehicleType);
                    return savedSpot;
                }
            }
        }

        throw new NoSpotAvailableException(
                String.format("No available spot for vehicle type: %s" , vehicleType));
    }

    @Override
    public void releaseSpot(Long spotId){
        ParkingSpot spot = spotRepository.findById(spotId)
                .orElseThrow(()-> new IllegalArgumentException("Spot not found"));
        spot.setStatus(ParkingSpotStatus.AVAILABLE);
        spot.setCurrentVehicle(null);
        spot.setOccupiedSince(null);
        spotRepository.save(spot);

        log.info("Released spot: {} successfully" , spot.getSpotNumber());
    }

    @Override
    public Long countAvailableSpots(VehicleType vehicleType){
        List<ParkingLot> lots = parkingLotRepository.findAll();
        if (lots.isEmpty()) {
            return 0L;
        }

        ParkingLot lot = lots.get(0);
        List<ParkingFloor> floors = floorRepository.findByParkingLotId(lot.getId());

        long totalAvailable = 0;
        for (ParkingFloor floor : floors) {
            Long count = spotRepository.countAvailableSpots(floor.getId(), ParkingSpotStatus.AVAILABLE);
            totalAvailable += count / vehicleType.getSpotsRequired();
        }

        return totalAvailable;
    }

}
