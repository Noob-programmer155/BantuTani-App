package com.team1.tm.bantutani.app.model.plants;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
public class PlantTypePest implements PlantType{
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true,nullable = false)
    private String type;
    @OneToMany(mappedBy = "plantTypePest")
    private List<PlantsPest> plantsPests = new LinkedList<>();

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PlantsPest> getPlantsPests() {
        return plantsPests;
    }

    public void setPlantsPests(List<PlantsPest> plantsPests) {
        this.plantsPests = plantsPests;
    }
}
