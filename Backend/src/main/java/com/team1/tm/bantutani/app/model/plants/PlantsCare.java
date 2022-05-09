package com.team1.tm.bantutani.app.model.plants;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.team1.tm.bantutani.app.model.Plants;
import com.team1.tm.bantutani.app.model.TipsNTrick;
import com.team1.tm.bantutani.app.model.User;
import com.team1.tm.bantutani.app.model.plants.dataAttr.CareType;

import javax.persistence.*;
import java.util.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
public class PlantsCare {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length=5000000)
    private String description;
    @Enumerated
    private CareType careType;
    private String animation;
    private String image;
    private String video;
    @ManyToOne(optional = false)
    @JoinTable
    private User authorPlantsCare;
    @ManyToOne
    @JoinTable
    private PlantsDisease plantsDiseaseCare;
    @ManyToOne
    @JoinTable
    private PlantsWeeds plantsWeedsCare;
    @ManyToOne
    @JoinTable
    private PlantsPest plantsPestCare;
    @OneToMany(mappedBy = "plantsCareTips", orphanRemoval = true)
    private List<TipsNTrick> tipsNTricks = new LinkedList<>();
    @ManyToMany(mappedBy = "caringPlants")
    private Set<Plants> plant = new LinkedHashSet<>();

    public PlantsCare() {}

    public PlantsCare(Builder builder) {
        this.description = builder.description;
        this.careType = builder.careType;
        this.animation = builder.animation;
        this.image = builder.image;
        this.video = builder.video;
        this.authorPlantsCare = builder.authorPlantsCare;
        this.plantsDiseaseCare = builder.plantsDiseaseCare;
        this.plantsWeedsCare = builder.plantsWeedsCare;
        this.plantsPestCare = builder.plantsPestCare;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CareType getCareType() {
        return careType;
    }

    public void setCareType(CareType careType) {
        this.careType = careType;
    }

    public User getAuthorPlantsCare() {
        return authorPlantsCare;
    }

    public void setAuthorPlantsCare(User authorPlantsCare) {
        this.authorPlantsCare = authorPlantsCare;
    }

    public PlantsDisease getPlantsDiseaseCare() {
        return plantsDiseaseCare;
    }

    public void setPlantsDiseaseCare(PlantsDisease plantsDiseaseCare) {
        this.plantsDiseaseCare = plantsDiseaseCare;
    }

    public PlantsWeeds getPlantsWeedsCare() {
        return plantsWeedsCare;
    }

    public void setPlantsWeedsCare(PlantsWeeds plantsWeedsCare) {
        this.plantsWeedsCare = plantsWeedsCare;
    }

    public PlantsPest getPlantsPestCare() {
        return plantsPestCare;
    }

    public void setPlantsPestCare(PlantsPest plantsPestCare) {
        this.plantsPestCare = plantsPestCare;
    }

    public String getAnimation() {
        return animation;
    }

    public void setAnimation(String animation) {
        this.animation = animation;
    }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public List<TipsNTrick> getTipsNTricks() {
        return tipsNTricks;
    }

    public void setTipsNTricks(List<TipsNTrick> tipsNTricks) { this.tipsNTricks = tipsNTricks; }

    public Set<Plants> getPlant() {
        return plant;
    }

    public void addPlants(Plants plants) {
        if(this.plant.contains(plants)) return ;
        this.plant.add(plants);
        plants.addCare(this);
    }
    public void removePlants(Plants plants) {
        if(!this.plant.contains(plants)) return ;
        this.plant.remove(plants);
        plants.removeCare(this);
    }

    public static class Builder {
        private String description;
        private CareType careType;
        private String animation;
        private String image;
        private String video;
        private User authorPlantsCare;
        private PlantsDisease plantsDiseaseCare;
        private PlantsWeeds plantsWeedsCare;
        private PlantsPest plantsPestCare;
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder careType(CareType careType) {
            this.careType = careType;
            return this;
        }
        public Builder animation(String animation) {
            this.animation = animation;
            return this;
        }
        public Builder image(String image) {
            this.image = image;
            return this;
        }
        public Builder video(String video) {
            this.video = video;
            return this;
        }
        public Builder plantsDiseaseCare(PlantsDisease plantsDisease) {
            this.plantsDiseaseCare = plantsDisease;
            return this;
        }
        public Builder plantsWeedsCare(PlantsWeeds plantsWeeds) {
            this.plantsWeedsCare = plantsWeeds;
            return this;
        }
        public Builder plantsPestCare(PlantsPest plantsPest) {
            this.plantsPestCare = plantsPest;
            return this;
        }
        public Builder author(User author) {
            this.authorPlantsCare = author;
            return this;
        }
        public PlantsCare build() {
            return new PlantsCare(this);
        }
    }
}
