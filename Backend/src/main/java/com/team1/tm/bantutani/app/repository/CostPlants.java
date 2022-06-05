package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.plants.CostPlant;
import com.team1.tm.bantutani.app.model.plants.PlantTypeImpl;

public interface CostPlants {
    public CostPlant getPlantsCost();
    public PlantTypeImpl getPlantTypeImpl();
    public String getName();
}
