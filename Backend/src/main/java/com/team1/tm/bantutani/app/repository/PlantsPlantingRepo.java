package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.plants.PlantsPlanting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantsPlantingRepo extends JpaRepository<PlantsPlanting, Long> {
    public List<PlantsPlanting> findAllByPlantingPlantsId(Long id);
}
