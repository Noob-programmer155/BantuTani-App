package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.Plants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantsRepo extends JpaRepository<Plants, Long> {
    @Query("select c from Plants c where c.id = :id")
    public PlantsResponse findPlantsById(Long id);
    @Query("select c from Plants c")
    public Page<PlantsResponseMin> findAllPlants(Pageable pageable);
}
