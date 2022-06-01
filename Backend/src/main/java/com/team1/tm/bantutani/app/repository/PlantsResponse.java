package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.plants.CostPlant;
import com.team1.tm.bantutani.app.model.plants.PlantTypeImpl;
import com.team1.tm.bantutani.app.model.plants.PlantsCare;
import com.team1.tm.bantutani.app.model.plants.PlantsPlanting;

import java.sql.Date;
import java.util.List;

public interface PlantsResponse {
    public Long getId();
    public String getName();
    public Date getDateUpdate();
    public List<String> getOtherNames();
    public PlantTypeImpl getPlantTypeImpl();
    public String getShortDescription();
    public String getCharacteristic();
    public List<String> getImage();
    public CostPlant getPlantsCost();
    public List<PlantsCare> getCares();
    public List<PlantsPlanting> getPlanting();
}
