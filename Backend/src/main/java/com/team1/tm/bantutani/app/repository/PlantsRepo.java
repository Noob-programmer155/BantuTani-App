package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.Plants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantsRepo extends JpaRepository<Plants, Long> {
    public PlantsResponse findPlantsById(Long id);
    public Page<PlantsResponseMin> findAllProjectedBy(Pageable pageable);
}
