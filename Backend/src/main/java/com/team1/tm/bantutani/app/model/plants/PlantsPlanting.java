package com.team1.tm.bantutani.app.model.plants;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.team1.tm.bantutani.app.model.Plants;
import com.team1.tm.bantutani.app.model.TipsNTrick;
import com.team1.tm.bantutani.app.model.User;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
public class PlantsPlanting {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 5000000)
    private String description;
    private String animation;
    private String image;
    private String video;
    @ManyToOne(optional = false)
    @JoinTable
    private User authorPlantsPlanting;
    @OneToMany(mappedBy = "plantsPlantingTips")
    private List<TipsNTrick> tipsNTricks = new LinkedList<>();
    @ManyToMany(mappedBy = "plantingPlants")
    private Set<Plants> plants = new LinkedHashSet<>();

    public PlantsPlanting() {
    }

    public PlantsPlanting(Builder builder) {
        this.description = builder.description;
        this.animation = builder.animation;
        this.image = builder.image;
        this.video = builder.video;
        this.authorPlantsPlanting = builder.authorPlantsPlanting;
        this.tipsNTricks = builder.tipsNTricks;
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

    public User getAuthorPlantsPlanting() {
        return authorPlantsPlanting;
    }

    public void setAuthorPlantsPlanting(User authorPlantsPlanting) {
        this.authorPlantsPlanting = authorPlantsPlanting;
    }

    public List<TipsNTrick> getTipsNTricks() {
        return tipsNTricks;
    }

    public void setTipsNTricks(List<TipsNTrick> tipsNTricks) {
        this.tipsNTricks = tipsNTricks;
    }

    public Set<Plants> getPlants() {
        return plants;
    }

    public void addPlants(Plants plants) {
        if(this.plants.contains(plants)) return ;
        this.plants.add(plants);
        plants.addPlanting(this);
    }
    public void removePlants(Plants plants) {
        if(!this.plants.contains(plants)) return ;
        this.plants.remove(plants);
        plants.removePlanting(this);
    }

    public static class Builder {
        private String description;
        private String animation;
        private String image;
        private String video;
        private User authorPlantsPlanting;
        private List<TipsNTrick> tipsNTricks = new LinkedList<>();
        public Builder description(String description) {
            this.description = description;
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
        public Builder author(User author) {
            this.authorPlantsPlanting = author;
            return this;
        }
        public Builder addTipsNTricks(List<TipsNTrick> tipsNTricks) {
            this.tipsNTricks.addAll(tipsNTricks);
            return this;
        }
        public Builder addTipsNTrick(TipsNTrick tipsNTrick) {
            this.tipsNTricks.add(tipsNTrick);
            return this;
        }
        public PlantsPlanting build() {
            return new PlantsPlanting(this);
        }
    }
}
