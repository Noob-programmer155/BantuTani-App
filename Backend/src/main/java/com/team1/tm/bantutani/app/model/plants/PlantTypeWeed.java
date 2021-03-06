package com.team1.tm.bantutani.app.model.plants;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
public class PlantTypeWeed implements PlantType{
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true,nullable = false)
    private String type;
    @OneToMany(mappedBy = "plantTypeWeed", cascade = CascadeType.ALL)
    private List<PlantsWeeds> plantsWeeds = new LinkedList<>();

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

    public List<PlantsWeeds> getPlantsWeeds() {
        return plantsWeeds;
    }

    public void setPlantsWeeds(List<PlantsWeeds> plantsWeeds) {
        this.plantsWeeds = plantsWeeds;
    }
}
