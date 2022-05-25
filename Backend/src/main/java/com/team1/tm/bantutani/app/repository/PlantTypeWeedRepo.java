package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.plants.PlantTypeWeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlantTypeWeedRepo extends JpaRepository<PlantTypeWeed, Long> {
    public boolean existsByType(String type);
    public Optional<PlantTypeWeed> findByType(String type);
    public Page<PlantTypeWeed> findAllByTypeContaining(String type, Pageable pageable);
    public void deleteByType(String type);
}
