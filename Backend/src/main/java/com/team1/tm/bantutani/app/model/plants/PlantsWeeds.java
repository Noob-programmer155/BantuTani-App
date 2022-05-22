package com.team1.tm.bantutani.app.model.plants;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.team1.tm.bantutani.app.model.Plants;
import com.team1.tm.bantutani.app.model.User;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
public class PlantsWeeds {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinTable(name = "PlantTypeWeedTable")
    private PlantTypeWeed plantTypeWeed;
    @ElementCollection
    private List<String> otherNames = new LinkedList<>();
    @Column(length = 500)
    private String description;
    @ElementCollection
    private List<String> images = new LinkedList<>();
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinTable(name = "AuthorPlantsWeedsTable")
    private User authorPlantsWeeds;
    @OneToMany(mappedBy = "plantsWeedsCare", orphanRemoval = true)
    private List<PlantsCare> plantsCares = new LinkedList<>();
    @ManyToMany(mappedBy = "weedPlants")
    private Set<Plants> plants = new LinkedHashSet<>();

    public PlantsWeeds() {
    }

    public PlantsWeeds(Builder builder) {
        this.name = builder.name;
        this.plantTypeWeed = builder.plantTypeWeed;
        this.otherNames = builder.otherNames;
        this.description = builder.description;
        this.images = builder.images;
        this.authorPlantsWeeds = builder.authorPlantsWeeds;
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

    public PlantTypeWeed getPlantTypeWeed() {
        return plantTypeWeed;
    }

    public void setPlantTypeWeed(PlantTypeWeed plantTypeWeed) {
        this.plantTypeWeed = plantTypeWeed;
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

    public void setImages(List<String> images) {
        this.images = images;
    }

    public User getAuthorPlantsWeeds() {
        return authorPlantsWeeds;
    }

    public void setAuthorPlantsWeeds(User authorPlantsWeeds) {
        this.authorPlantsWeeds = authorPlantsWeeds;
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
        if (this.plants.contains(plants)) return ;
        this.plants.add(plants);
        plants.addWeeds(this);
    }
    public void removePlants(Plants plants) {
        if (!this.plants.contains(plants)) return ;
        this.plants.remove(plants);
        plants.removeWeeds(this);
    }

    public static class Builder {
        private String name;
        private PlantTypeWeed plantTypeWeed;
        private List<String> otherNames;
        private String description;
        private List<String> images;
        private User authorPlantsWeeds;
        private List<PlantsCare> plantsCares = new LinkedList<>();
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder plantTypeWeed(PlantTypeWeed plantTypeWeed) {
            this.plantTypeWeed = plantTypeWeed;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder author(User author) {
            this.authorPlantsWeeds = author;
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
        public PlantsWeeds build() {
            return new PlantsWeeds(this);
        }
    }
}
