package com.SmartParkingLotSystem.SmartParkingLotSystem.repository;

import com.SmartParkingLotSystem.SmartParkingLotSystem.entity.FeeStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeeStructureRepository extends JpaRepository<FeeStructure , Long> {
    Optional<FeeStructure> findByNameAndIsActiveTrue(String name);
    Optional<FeeStructure> findFirstByIsActiveTrueOrderByCreatedAtDesc();
}
