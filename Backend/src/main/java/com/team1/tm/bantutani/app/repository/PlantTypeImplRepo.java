package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.plants.PlantTypeImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlantTypeImplRepo extends JpaRepository<PlantTypeImpl, Long> {
    public boolean existsByType(String type);
    public Optional<PlantTypeImpl> findByType(String type);
    public Page<PlantTypeImpl> findAllByTypeContaining(String type, Pageable pageable);
    public void deleteByType(String type);
}
