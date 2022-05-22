package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.TipsNTrick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipsNTrickRepo extends JpaRepository<TipsNTrick, Long> {
    public List<TipsNTrick> findAllByPlantsCareTipsId(Long id);
    public List<TipsNTrick> findAllByPlantsPlantingTipsId(Long id);
}
