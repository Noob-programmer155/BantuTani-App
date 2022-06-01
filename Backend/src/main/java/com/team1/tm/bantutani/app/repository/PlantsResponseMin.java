package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.plants.PlantTypeImpl;

import java.sql.Date;
import java.util.List;

public interface PlantsResponseMin {
    public Long getId();
    public String getName();
    public PlantTypeImpl getPlantTypeImpl();
    public List<String> getImage();
    public Date getDateUpdate();
}
