package com.team1.tm.bantutani.app.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.team1.tm.bantutani.app.model.plants.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
public class Plants {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Date dateUpdate;
    @ElementCollection
    private List<String> otherNames;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinTable(name="PlantTypeImplTable")
    private PlantTypeImpl plantTypeImpl;
    @ElementCollection
    private List<String> image = new LinkedList<>();
    @Column(length = 500)
    private String shortDescription;
    @Column(length = 5000000)
    private String characteristic;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private CostPlant plantsCost;
    @OneToMany(mappedBy = "caringPlants", orphanRemoval = true)
    private List<PlantsCare> cares = new LinkedList<>();
    @OneToMany(mappedBy = "plantingPlants", orphanRemoval = true)
    private List<PlantsPlanting> planting = new LinkedList<>();
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "DiseasePlantsTable")
    private Set<PlantsDisease> diseasePlants = new LinkedHashSet<>();
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "WeedPlantsTable")
    private Set<PlantsWeeds> weedPlants = new LinkedHashSet<>();
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "PestPlantsTable")
    private Set<PlantsPest> pestPlants = new LinkedHashSet<>();

    public Plants() {
    }

    public Plants(Builder builder) {
        this.name = builder.name;
        this.dateUpdate = builder.dateUpdate;
        this.otherNames = builder.otherNames;
        this.plantTypeImpl = builder.plantTypeImpl;
        this.shortDescription = builder.shortDescription;
        this.image = builder.image;
        this.characteristic = builder.characteristic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public List<String> getOtherNames() { return otherNames; }

    public void setOtherNames(List<String> otherNames) { this.otherNames = otherNames; }

    public PlantTypeImpl getPlantTypeImpl() {
        return plantTypeImpl;
    }

    public void setPlantTypeImpl(PlantTypeImpl plantTypeImpl) {
        this.plantTypeImpl = plantTypeImpl;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public List<String> getImage() { return image; }

    public void setImage(List<String> image) { this.image = image; }

    public CostPlant getPlantsCost() {
        return plantsCost;
    }

    public void setPlantsCost(CostPlant plantsCost) {
        this.plantsCost = plantsCost;
    }

    public List<PlantsCare> getCares() {
        return cares;
    }

    public void setCares(List<PlantsCare> cares) {
        this.cares = cares;
    }

    public List<PlantsPlanting> getPlanting() {
        return planting;
    }

    public void setPlanting(List<PlantsPlanting> planting) {
        this.planting = planting;
    }

    public Set<PlantsDisease> getDiseasePlants() {
        return diseasePlants;
    }

    public void addDisease(PlantsDisease plantsDisease) {
        if(this.diseasePlants.contains(plantsDisease)) return ;
        this.diseasePlants.add(plantsDisease);
        plantsDisease.addPlants(this);
    }
    public void removeDisease(PlantsDisease plantsDisease) {
        if(!this.diseasePlants.contains(plantsDisease))return ;
        this.diseasePlants.remove(plantsDisease);
        plantsDisease.removePlants(this);
    }

    public Set<PlantsWeeds> getWeedPlants() {
        return weedPlants;
    }

    public void addWeeds(PlantsWeeds plantsWeeds) {
        if (this.weedPlants.contains(plantsWeeds)) return ;
        this.weedPlants.add(plantsWeeds);
        plantsWeeds.addPlants(this);
    }
    public void removeWeeds(PlantsWeeds plantsWeeds) {
        if (!this.weedPlants.contains(plantsWeeds)) return ;
        this.weedPlants.remove(plantsWeeds);
        plantsWeeds.removePlants(this);
    }

    public Set<PlantsPest> getPestPlants() {
        return pestPlants;
    }

    public void addPest(PlantsPest plantsPest) {
        if (this.pestPlants.contains(plantsPest)) return ;
        this.pestPlants.add(plantsPest);
        plantsPest.addPlants(this);
    }
    public void removePest(PlantsPest plantsPest) {
        if (!this.pestPlants.contains(plantsPest)) return ;
        this.pestPlants.remove(plantsPest);
        plantsPest.removePlants(this);
    }

    public static class Builder {
        private String name;
        private Date dateUpdate;
        private List<String> otherNames;
        private PlantTypeImpl plantTypeImpl;
        private List<String> image;
        private String shortDescription;
        private String characteristic;
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder dateUpdate(Date date) {
            this.dateUpdate = date;
            return this;
        }
        public Builder otherNames(List<String> otherNames) {
            this.otherNames = otherNames;
            return this;
        }
        public Builder plantType(PlantTypeImpl plantTypeImpl) {
            this.plantTypeImpl = plantTypeImpl;
            return this;
        }
        public Builder image(List<String> image) {
            this.image = image;
            return this;
        }
        public Builder shortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }
        public Builder characteristic(String characteristic) {
            this.characteristic = characteristic;
            return this;
        }
        public Plants build() {
            return new Plants(this);
        }
    }
}
