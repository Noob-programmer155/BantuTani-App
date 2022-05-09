package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.Plants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantsRepo extends JpaRepository<Plants, Long> {
}
