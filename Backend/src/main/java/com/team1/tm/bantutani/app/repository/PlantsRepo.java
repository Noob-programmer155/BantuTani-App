package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.Plants;
import com.team1.tm.bantutani.app.model.plants.PlantsTitle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlantsRepo extends JpaRepository<Plants, Long> {
    public PlantsResponse findPlantsById(Long id);
    public Page<PlantsResponseMin> findDistinctProjectedBy(Pageable pageable);
    public List<PlantsTitle> findDistinctByNameContaining(String name, Pageable pageable);
    public Page<PlantsResponseMin> findAllDistinctByNameContaining(String name, Pageable pageable);
}
