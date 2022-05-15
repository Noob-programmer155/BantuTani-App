package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.plants.PlantTypeDisease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantTypeDiseaseRepo extends JpaRepository<PlantTypeDisease, Long> {
}
