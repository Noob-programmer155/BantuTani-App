package com.team1.tm.bantutani.app.dto;

import com.team1.tm.bantutani.app.model.Plants;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PlantsPlantingDTO {
    private Long id;
    private String description;
    private String animation;
    private MultipartFile image;
    private String video;
    private Long authorPlantsPlanting;
    private List<TipsNTrickDTO> tipsNTricks;
    private Long plantingPlants;

    public PlantsPlantingDTO() {
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Long getAuthorPlantsPlanting() {
        return authorPlantsPlanting;
    }

    public void setAuthorPlantsPlanting(Long authorPlantsPlanting) {
        this.authorPlantsPlanting = authorPlantsPlanting;
    }

    public List<TipsNTrickDTO> getTipsNTricks() {
        return tipsNTricks;
    }

    public void setTipsNTricks(List<TipsNTrickDTO> tipsNTricks) {
        this.tipsNTricks = tipsNTricks;
    }

    public Long getPlantingPlants() {
        return plantingPlants;
    }

    public void setPlantingPlants(Long plantingPlants) {
        this.plantingPlants = plantingPlants;
    }
}
