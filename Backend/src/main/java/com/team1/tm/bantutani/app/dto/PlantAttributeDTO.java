package com.team1.tm.bantutani.app.dto;

import com.fasterxml.jackson.databind.deser.BuilderBasedDeserializer;
import com.team1.tm.bantutani.app.model.plants.PlantsCare;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PlantAttributeDTO {
    private Long id;
    private String name;
    private Long attributePlantsType;
    private List<String> otherNames;
    private String description;
    private List<MultipartFile> images;
    private Long authorPlantsAttribute;
    private Long plants;

    public PlantAttributeDTO() {
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

    public Long getAttributePlantsType() {
        return attributePlantsType;
    }

    public void setAttributePlantsType(Long attributePlantsType) {
        this.attributePlantsType = attributePlantsType;
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

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public Long getAuthorPlantsAttribute() {
        return authorPlantsAttribute;
    }

    public void setAuthorPlantsAttribute(Long authorPlantsAttribute) {
        this.authorPlantsAttribute = authorPlantsAttribute;
    }

    public Long getPlants() {
        return plants;
    }

    public void setPlants(Long plants) {
        this.plants = plants;
    }
}
