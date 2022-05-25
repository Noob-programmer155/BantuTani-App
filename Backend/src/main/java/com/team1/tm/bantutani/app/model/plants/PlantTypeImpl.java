package com.team1.tm.bantutani.app.model.plants;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.team1.tm.bantutani.app.model.Plants;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
public class PlantTypeImpl implements PlantType{
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true,nullable = false)
    private String type;
    private Boolean icon;
    @OneToMany(mappedBy = "plantTypeImpl")
    private List<Plants> plants = new LinkedList<>();

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Plants> getPlants() {
        return plants;
    }

    public void setPlants(List<Plants> plants) {
        this.plants = plants;
    }

    public Boolean getIcon() {
        return icon;
    }

    public void setIcon(Boolean icon) {
        this.icon = icon;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
