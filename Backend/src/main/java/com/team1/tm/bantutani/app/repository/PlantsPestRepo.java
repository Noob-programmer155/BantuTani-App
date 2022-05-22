package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.plants.PlantsPest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Arrays;
import java.util.List;

@Repository
public interface PlantsPestRepo extends JpaRepository<PlantsPest, Long> {
    public Page<PlantsPest> findAllByPlantsId(Long id, Pageable pageable);
}
