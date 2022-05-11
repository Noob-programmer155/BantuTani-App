package com.team1.tm.bantutani.app.model.plants;

import com.team1.tm.bantutani.app.model.Plants;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class PlantTypeDisease implements PlantType{
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true,nullable = false)
    private String type;
    @OneToMany(mappedBy = "plantTypeDisease")
    private List<PlantsDisease> plantsDiseases = new LinkedList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PlantsDisease> getPlantsDiseases() {
        return plantsDiseases;
    }

    public void setPlantsDiseases(List<PlantsDisease> plantsDiseases) {
        this.plantsDiseases = plantsDiseases;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }
}
