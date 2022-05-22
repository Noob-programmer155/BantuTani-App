package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.plants.PlantsDisease;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantsDiseaseRepo extends JpaRepository<PlantsDisease, Long> {
    public Page<PlantsDisease> findAllByPlantsId(Long id, Pageable pageable);
}
