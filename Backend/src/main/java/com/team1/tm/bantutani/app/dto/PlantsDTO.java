package com.team1.tm.bantutani.app.dto;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

public class PlantsDTO {
    private Long id;
    private String name;
    private List<String> otherNames;
    private Long plantTypeImpl;
    private List<MultipartFile> image;
    private String shortDescription;
    private String characteristic;
    private int stableCost; // current cost
    private int maxCost;
    private int minCost;
    private int regionCost; // mean
    private Date dateUpdateCost;

    public PlantsDTO() {
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

    public Long getPlantTypeImpl() {
        return plantTypeImpl;
    }

    public void setPlantTypeImpl(Long plantTypeImpl) {
        this.plantTypeImpl = plantTypeImpl;
    }

    public List<MultipartFile> getImage() {
        return image;
    }

    public void setImage(List<MultipartFile> image) {
        this.image = image;
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
}
