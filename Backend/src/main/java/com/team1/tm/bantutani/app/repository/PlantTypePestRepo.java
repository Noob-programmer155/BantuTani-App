package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.plants.PlantTypePest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlantTypePestRepo extends JpaRepository<PlantTypePest, Long> {
    public boolean existsByType(String type);
    public Optional<PlantTypePest> findByType(String type);
    public Page<PlantTypePest> findAllByTypeContaining(String type, Pageable pageable);
    public void deleteByType(String type);
}
