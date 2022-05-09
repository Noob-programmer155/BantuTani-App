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
public class PlantsPest {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ElementCollection
    private List<String> otherNames = new LinkedList<>();
    @Column(length = 500)
    private String description;
    @ElementCollection
    private List<String> images = new LinkedList<>();
    @ManyToOne(optional = false)
    @JoinTable
    private User authorPlantsPest;
    @OneToMany(mappedBy = "plantsPestCare")
    private List<PlantsCare> plantsCares = new LinkedList<>();
    @ManyToMany(mappedBy = "pestPlants")
    private Set<Plants> plants = new LinkedHashSet<>();

    public PlantsPest() {
    }

    public PlantsPest(Builder builder) {
        this.name = builder.name;
        this.otherNames = builder.otherNames;
        this.description = builder.description;
        this.images = builder.images;
        this.authorPlantsPest = builder.authorPlantsPest;
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

    public User getAuthorPlantsPest() {
        return authorPlantsPest;
    }

    public void setAuthorPlantsPest(User authorPlantsPest) {
        this.authorPlantsPest = authorPlantsPest;
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
        plants.addPest(this);
    }
    public void removePlants(Plants plants) {
        if (!this.plants.contains(plants)) return ;
        this.plants.remove(plants);
        plants.removePest(this);
    }

    public static class Builder {
        private String name;
        private List<String> otherNames;
        private String description;
        private List<String> images;
        private User authorPlantsPest;
        private List<PlantsCare> plantsCares = new LinkedList<>();
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder author(User author) {
            this.authorPlantsPest = author;
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
        public PlantsPest build() {
            return new PlantsPest(this);
        }
    }
}
