package com.team1.tm.bantutani.app.dto.response;

import com.team1.tm.bantutani.app.dto.PlantsCareDTO;
import com.team1.tm.bantutani.app.dto.PlantsPlantingDTO;

import java.sql.Date;
import java.util.List;

public class PlantsResponseDTO {
    private Long id;
    private String name;
    private List<String> otherNames;
    private String plantTypeImpl;
    private List<String> image;
    private String shortDescription;
    private String characteristic;
    private int stableCost; // current cost
    private int maxCost;
    private int minCost;
    private int regionCost; // mean
    private Date dateUpdateCost;
    private Date dateUpdate;
    private List<PlantsCareResponseDTO> cares;
    private List<PlantsPlantingResponseDTO> planting;

    public PlantsResponseDTO() {
    }

    public PlantsResponseDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.otherNames = builder.otherNames;
        this.plantTypeImpl = builder.plantTypeImpl;
        this.image = builder.image;
        this.shortDescription = builder.shortDescription;
        this.characteristic = builder.characteristic;
        this.stableCost = builder.stableCost;
        this.maxCost = builder.maxCost;
        this.minCost = builder.minCost;
        this.regionCost = builder.regionCost;
        this.dateUpdateCost = builder.dateUpdateCost;
        this.dateUpdate = builder.dateUpdate;
        this.cares = builder.cares;
        this.planting = builder.planting;
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

    public String getPlantTypeImpl() {
        return plantTypeImpl;
    }

    public void setPlantTypeImpl(String plantTypeImpl) {
        this.plantTypeImpl = plantTypeImpl;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
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

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public List<PlantsCareResponseDTO> getCares() {
        return cares;
    }

    public void setCares(List<PlantsCareResponseDTO> cares) {
        this.cares = cares;
    }

    public List<PlantsPlantingResponseDTO> getPlanting() {
        return planting;
    }

    public void setPlanting(List<PlantsPlantingResponseDTO> planting) {
        this.planting = planting;
    }

    public static class Builder {
        private Long id;
        private String name;
        private List<String> otherNames;
        private String plantTypeImpl;
        private List<String> image;
        private String shortDescription;
        private String characteristic;
        private int stableCost; // mean
        private int maxCost;
        private int minCost;
        private int regionCost; // current cost
        private Date dateUpdateCost;
        private Date dateUpdate;
        private List<PlantsCareResponseDTO> cares;
        private List<PlantsPlantingResponseDTO> planting;
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder otherNames(List<String> otherNames) {
            this.otherNames = otherNames;
            return this;
        }
        public Builder type(String type) {
            this.plantTypeImpl = type;
            return this;
        }
        public Builder image(List<String> image) {
            this.image = image;
            return this;
        }
        public Builder shortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }
        public Builder characteristic(String characteristic) {
            this.characteristic = characteristic;
            return this;
        }
        public Builder stableCost(int stableCost) {
            this.stableCost = stableCost;
            return this;
        }
        public Builder maxCost(int maxCost) {
            this.maxCost = maxCost;
            return this;
        }
        public Builder minCost(int minCost) {
            this.minCost = minCost;
            return this;
        }
        public Builder regionCost(int regionCost) {
            this.regionCost = regionCost;
            return this;
        }
        public Builder date(Date date) {
            this.dateUpdateCost = date;
            return this;
        }
        public Builder dateUpdate(Date date) {
            this.dateUpdate = date;
            return this;
        }
        public Builder cares(List<PlantsCareResponseDTO> cares) {
            this.cares = cares;
            return this;
        }
        public Builder planting(List<PlantsPlantingResponseDTO> planting) {
            this.planting = planting;
            return this;
        }
        public PlantsResponseDTO build() {
            return new PlantsResponseDTO(this);
        }
    }
}
