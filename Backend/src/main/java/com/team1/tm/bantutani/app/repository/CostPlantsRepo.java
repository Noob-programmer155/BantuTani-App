package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.plants.CostPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CostPlantsRepo extends JpaRepository<CostPlant, Long> {
    public Optional<CostPlant> findByPlantsId(Long id);
}
