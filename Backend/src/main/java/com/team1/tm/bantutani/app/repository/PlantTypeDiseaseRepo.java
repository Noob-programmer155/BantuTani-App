package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.plants.PlantTypeDisease;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlantTypeDiseaseRepo extends JpaRepository<PlantTypeDisease, Long> {
    public boolean existsByType(String type);
    public Optional<PlantTypeDisease> findByType(String type);
    public Page<PlantTypeDisease> findAllByTypeContaining(String type, Pageable pageable);
    public void deleteByType(String type);
}
