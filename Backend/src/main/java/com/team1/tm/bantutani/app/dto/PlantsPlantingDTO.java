package com.team1.tm.bantutani.app.dto;

import com.team1.tm.bantutani.app.model.Plants;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PlantsPlantingDTO {
    private Long id;
    private Long step;
    private String title;
    private String description;
    private String animation;
    private MultipartFile image;
    private String video;
    private Long authorPlantsPlanting;
    private Long plantingPlants;

    public PlantsPlantingDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStep() {
        return step;
    }

    public void setStep(Long step) {
        this.step = step;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Long getPlantingPlants() {
        return plantingPlants;
    }

    public void setPlantingPlants(Long plantingPlants) {
        this.plantingPlants = plantingPlants;
    }
}
