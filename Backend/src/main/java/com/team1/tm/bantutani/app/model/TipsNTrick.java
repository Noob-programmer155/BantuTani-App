package com.team1.tm.bantutani.app.model;

import com.team1.tm.bantutani.app.model.other.TypeActivity;
import com.team1.tm.bantutani.app.model.other.TypeStep;
import com.team1.tm.bantutani.app.model.plants.PlantsCare;
import com.team1.tm.bantutani.app.model.plants.PlantsPlanting;

import javax.persistence.*;
import java.util.List;

@Entity
public class TipsNTrick {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 500)
    private String name; // Tips for Planting
    @Column(length = 5000000)
    private String description;
    private String animation;
    @Enumerated
    private TypeStep typeStep;
    @Enumerated
    private TypeActivity typeActivity;
    @ManyToOne(optional = false)
    @JoinTable
    private User authorTipsNTrick;
    @ManyToOne
    @JoinTable
    private PlantsCare plantsCareTips;
    @ManyToOne
    @JoinTable
    private PlantsPlanting plantsPlantingTips;

    public TipsNTrick() {
    }

    public TipsNTrick(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.animation = builder.animation;
        this.typeStep = builder.typeStep;
        this.typeActivity = builder.typeActivity;
        this.authorTipsNTrick = builder.authorTipsNTrick;
        this.plantsCareTips = builder.plantsCareTips;
        this.plantsPlantingTips = builder.plantsPlantingTips;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnimation() { return animation; }

    public void setAnimation(String animation) {
        this.animation = animation;
    }

    public TypeStep getTypeStep() {
        return typeStep;
    }

    public void setTypeStep(TypeStep typeStep) { this.typeStep = typeStep; }

    public TypeActivity getTypeActivity() { return typeActivity; }

    public void setTypeActivity(TypeActivity typeActivity) { this.typeActivity = typeActivity; }

    public User getAuthorTipsNTrick() {
        return authorTipsNTrick;
    }

    public void setAuthorTipsNTrick(User authorTipsNTrick) {
        this.authorTipsNTrick = authorTipsNTrick;
    }

    public PlantsCare getPlantsCareTips() {
        return plantsCareTips;
    }

    public void setPlantsCareTips(PlantsCare plantsCareTips) {
        this.plantsCareTips = plantsCareTips;
    }

    public PlantsPlanting getPlantsPlantingTips() {
        return plantsPlantingTips;
    }

    public void setPlantsPlantingTips(PlantsPlanting plantsPlantingTips) {
        this.plantsPlantingTips = plantsPlantingTips;
    }

    public static class Builder {
        private String name; // Tips for Planting
        private String description;
        private String animation;
        private TypeStep typeStep;
        private TypeActivity typeActivity;
        private User authorTipsNTrick;
        private PlantsCare plantsCareTips;
        private PlantsPlanting plantsPlantingTips;
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder animation(String animation) {
            this.animation = animation;
            return this;
        }
        public Builder typeStep(TypeStep typeStep) {
            this.typeStep = typeStep;
            return this;
        }
        public Builder typeActivity(TypeActivity typeActivity) {
            this.typeActivity = typeActivity;
            return this;
        }
        public Builder author(User author) {
            this.authorTipsNTrick = author;
            return this;
        }
        public Builder plantsCareTips(PlantsCare plantsCare) {
            this.plantsCareTips = plantsCare;
            return this;
        }
        public Builder plantsPlantingTips(PlantsPlanting plantsPlanting) {
            this.plantsPlantingTips = plantsPlanting;
            return this;
        }
        public TipsNTrick build() {
            return new TipsNTrick(this);
        }
    }
}
