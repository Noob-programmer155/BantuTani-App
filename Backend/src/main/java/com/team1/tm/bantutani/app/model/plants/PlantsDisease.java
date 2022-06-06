package com.team1.tm.bantutani.app.model.plants;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.team1.tm.bantutani.app.model.Plants;
import com.team1.tm.bantutani.app.model.TipsNTrick;
import com.team1.tm.bantutani.app.model.User;

import java.sql.Date;
import javax.persistence.*;
import java.util.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
public class PlantsDisease {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Date dateUpdate;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinTable(name = "PlantTypeDiseaseTable")
    private PlantTypeDisease plantTypeDisease;
    @ElementCollection
    private List<String> otherNames = new LinkedList<>();
    @Column(length = 2000)
    private String description;
    @ElementCollection
    private List<String> images = new LinkedList<>();
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinTable(name = "AuthorPlantsDiseaseTable")
    private User authorPlantsAttribute;
    @OneToMany(mappedBy = "plantsDiseaseCare", orphanRemoval = true)
    private List<PlantsCare> plantsCares = new LinkedList<>();
    @ManyToMany(mappedBy = "diseasePlants")
    private Set<Plants> plants = new LinkedHashSet<>();

    public PlantsDisease() {
    }

    public PlantsDisease(Builder builder) {
        this.name = builder.name;
        this.dateUpdate = builder.dateUpdate;
        this.plantTypeDisease = builder.plantTypeDisease;
        this.otherNames = builder.otherNames;
        this.description = builder.description;
        this.images = builder.images;
        this.authorPlantsAttribute = builder.authorPlantsAttribute;
        this.plantsCares = builder.plantsCares;
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

    public PlantTypeDisease getPlantTypeDisease() {
        return plantTypeDisease;
    }

    public void setPlantTypeDisease(PlantTypeDisease plantTypeDisease) {
        this.plantTypeDisease = plantTypeDisease;
    }

    public List<String> getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(List<String> otherNames) {
        this.otherNames = otherNames;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) { this.images = images; }

    public User getAuthorPlantsAttribute() {
        return authorPlantsAttribute;
    }

    public void setAuthorPlantsAttribute(User authorPlantsAttribute) {
        this.authorPlantsAttribute = authorPlantsAttribute;
    }

    public List<PlantsCare> getPlantsCares() {
        return plantsCares;
    }

    public void setPlantsCares(List<PlantsCare> plantsCares) {
        this.plantsCares = plantsCares;
    }

    public Set<Plants> getPlants() {
        return plants;
    }

    public void addPlants(Plants plants) {
        if(this.plants.contains(plants)) return ;
        this.plants.add(plants);
        plants.addDisease(this);
    }
    public void removePlants(Plants plants) {
        if(!this.plants.contains(plants)) return ;
        this.plants.remove(plants);
        plants.removeDisease(this);
    }

    public static class Builder {
        private String name;
        private Date dateUpdate;
        private PlantTypeDisease plantTypeDisease;
        private List<String> otherNames;
        private String description;
        private List<String> images;
        private User authorPlantsAttribute;
        private List<PlantsCare> plantsCares = new LinkedList<>();
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder dateUpdate(Date date) {
            this.dateUpdate = date;
            return this;
        }
        public Builder plantTypeDisease(PlantTypeDisease plantTypeDisease) {
            this.plantTypeDisease = plantTypeDisease;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder author(User author) {
            this.authorPlantsAttribute = author;
            return this;
        }
        public Builder addOtherNames(List<String> otherNames) {
            this.otherNames = otherNames;
            return this;
        }
        public Builder addImages(List<String> images) {
            this.images = images;
            return this;
        }
        public Builder addPlantsCares(List<PlantsCare> plantsCares) {
            this.plantsCares.addAll(plantsCares);
            return this;
        }
        public Builder addPlantsCare(PlantsCare plantsCare) {
            this.plantsCares.add(plantsCare);
            return this;
        }
        public PlantsDisease build() {
            return new PlantsDisease(this);
        }
    }
}
