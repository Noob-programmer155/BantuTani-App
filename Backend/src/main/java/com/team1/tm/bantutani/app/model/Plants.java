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
    @ElementCollection
    private List<String> otherNames;
    private String plantType;
    private String image;
    @Column(length = 500)
    private String shortDescription;
    @Column(length = 5000000)
    private String characteristic;
    private int stableCost; // mean
    private int maxCost;
    private int minCost;
    private int regionCost; // current cost
    private Date dateUpdateCost;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable
    private Set<PlantsCare> caringPlants = new LinkedHashSet<>();
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable
    private Set<PlantsPlanting> plantingPlants = new LinkedHashSet<>();
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable
    private Set<PlantsDisease> diseasePlants = new LinkedHashSet<>();
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable
    private Set<PlantsWeeds> weedPlants = new LinkedHashSet<>();
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable
    private Set<PlantsPest> pestPlants = new LinkedHashSet<>();

    public Plants() {
    }

    public Plants(Builder builder) {
        this.name = builder.name;
        this.otherNames = builder.otherNames;
        this.plantType = builder.plantType;
        this.shortDescription = builder.shortDescription;
        this.image = builder.image;
        this.characteristic = builder.characteristic;
        this.stableCost = builder.stableCost;
        this.maxCost = builder.maxCost;
        this.minCost = builder.minCost;
        this.regionCost = builder.regionCost;
        this.dateUpdateCost = builder.dateUpdateCost;
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

    public List<String> getOtherNames() { return otherNames; }

    public void setOtherNames(List<String> otherNames) { this.otherNames = otherNames; }

    public String getPlantType() {
        return plantType;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
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

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public int getStableCost() {
        return stableCost;
    }

    public void setStableCost(int stableCost) {
        this.stableCost = stableCost;
    }

    public int getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(int maxCost) {
        this.maxCost = maxCost;
    }

    public int getMinCost() {
        return minCost;
    }

    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }

    public int getRegionCost() {
        return regionCost;
    }

    public void setRegionCost(int regionCost) {
        this.regionCost = regionCost;
    }

    public Date getDateUpdateCost() {
        return dateUpdateCost;
    }

    public void setDateUpdateCost(Date dateUpdateCost) {
        this.dateUpdateCost = dateUpdateCost;
    }

    public Set<PlantsCare> getCaringPlants() {
        return caringPlants;
    }

    public void addCare(PlantsCare plantsCare) {
        if(this.caringPlants.contains(plantsCare)) return ;
        this.caringPlants.add(plantsCare);
        plantsCare.addPlants(this);
    }
    public void removeCare(PlantsCare plantsCare) {
        if(!this.caringPlants.contains(plantsCare)) return ;
        this.caringPlants.remove(plantsCare);
        plantsCare.removePlants(this);
    }

    public Set<PlantsPlanting> getPlantingPlants() {
        return plantingPlants;
    }

    public void addPlanting(PlantsPlanting plantsPlanting) {
        if(this.plantingPlants.contains(plantsPlanting)) return ;
        this.plantingPlants.add(plantsPlanting);
        plantsPlanting.addPlants(this);
    }
    public void removePlanting(PlantsPlanting plantsPlanting) {
        if(!this.plantingPlants.contains(plantsPlanting)) return ;
        this.plantingPlants.remove(plantsPlanting);
        plantsPlanting.removePlants(this);
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
        private List<String> otherNames;
        private String plantType;
        private String image;
        private String shortDescription;
        private String characteristic;
        private int stableCost; // mean
        private int maxCost;
        private int minCost;
        private int regionCost; // current cost
        private Date dateUpdateCost;
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder otherNames(List<String> otherNames) {
            this.otherNames = otherNames;
            return this;
        }
        public Builder plantType(String plantType) {
            this.plantType = plantType;
            return this;
        }
        public Builder image(String image) {
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
        public Builder stableCost(int stableCost) {
            this.stableCost = stableCost;
            return this;
        }
        public Builder maxCost(int maxCost) {
            this.maxCost = maxCost;
            return this;
        }
        public Builder minCost(int minCost) {
            this.minCost = minCost;
            return this;
        }
        public Builder regionCost(int regionCost) {
            this.regionCost = regionCost;
            return this;
        }
        public Builder dateUpdateCost(Date dateUpdateCost) {
            this.dateUpdateCost = dateUpdateCost;
            return this;
        }
        public Plants build() {
            return new Plants(this);
        }
    }
}
