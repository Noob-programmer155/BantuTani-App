package com.team1.tm.bantutani.app.model.plants;

import com.team1.tm.bantutani.app.model.Plants;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class CostPlant {
    @Id
    @GeneratedValue
    private Long id;
    private Integer stableCost; // current cost
    private Integer maxCost;
    private Integer minCost;
    private Integer regionCost; // mean
    private Integer previousCost;
    private Date dateUpdateCost;
    @OneToOne(mappedBy = "plantsCost", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Plants plants;

    public CostPlant() {
    }

    public CostPlant(Builder builder) {
        this.stableCost = builder.stableCost;
        this.maxCost = builder.maxCost;
        this.minCost = builder.minCost;
        this.regionCost = builder.regionCost;
        this.previousCost = builder.previousCost;
        this.dateUpdateCost = builder.dateUpdateCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStableCost() {
        return stableCost;
    }

    public void setStableCost(Integer stableCost) {
        this.stableCost = stableCost;
    }

    public Integer getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(Integer maxCost) {
        this.maxCost = maxCost;
    }

    public Integer getMinCost() {
        return minCost;
    }

    public void setMinCost(Integer minCost) {
        this.minCost = minCost;
    }

    public Integer getRegionCost() {
        return regionCost;
    }

    public void setRegionCost(Integer regionCost) {
        this.regionCost = regionCost;
    }

    public Integer getPreviousCost() {
        return previousCost;
    }

    public void setPreviousCost(Integer previousCost) {
        this.previousCost = previousCost;
    }

    public Date getDateUpdateCost() {
        return dateUpdateCost;
    }

    public void setDateUpdateCost(Date dateUpdateCost) {
        this.dateUpdateCost = dateUpdateCost;
    }

    public Plants getPlants() {
        return plants;
    }

    public void setPlants(Plants plants) {
        this.plants = plants;
    }

    public static class Builder {
        private Integer stableCost; // current cost
        private Integer maxCost;
        private Integer minCost;
        private Integer regionCost; // mean
        private Integer previousCost;
        private Date dateUpdateCost;
        public Builder stableCost(Integer stableCost) {
            this.stableCost = stableCost;
            return this;
        }
        public Builder maxCost(Integer maxCost) {
            this.maxCost = maxCost;
            return this;
        }
        public Builder minCost(Integer minCost) {
            this.minCost = minCost;
            return this;
        }
        public Builder regionCost(Integer regionCost) {
            this.regionCost = regionCost;
            return this;
        }
        public Builder previousCost(Integer previousCost) {
            this.previousCost = previousCost;
            return this;
        }
        public Builder dateUpdateCost(Date dateUpdateCost) {
            this.dateUpdateCost = dateUpdateCost;
            return this;
        }
        public CostPlant build() {
            return new CostPlant(this);
        }
    }
}
