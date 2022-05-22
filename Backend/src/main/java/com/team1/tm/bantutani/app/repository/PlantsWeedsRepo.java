package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.plants.PlantsWeeds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface PlantsWeedsRepo extends JpaRepository<PlantsWeeds, Long> {
    public Page<PlantsWeeds> findAllByPlantsId(Long id, Pageable pageable);
}