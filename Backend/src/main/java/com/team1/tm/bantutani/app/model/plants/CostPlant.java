package com.team1.tm.bantutani.app.model.plants;

import com.team1.tm.bantutani.app.model.Plants;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class CostPlant {
    @Id
    @GeneratedValue
    private Long id;
    private int stableCost; // current cost
    private int maxCost;
    private int minCost;
    private int regionCost; // mean
    private Date dateUpdateCost;
    @OneToOne(mappedBy = "plantsCost", fetch = FetchType.LAZY)
    private Plants plants;

    public CostPlant() {
    }

    public CostPlant(Builder builder) {
        this.stableCost = builder.stableCost;
        this.maxCost = builder.maxCost;
        this.minCost = builder.minCost;
        this.regionCost = builder.regionCost;
        this.dateUpdateCost = builder.dateUpdateCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Plants getPlants() {
        return plants;
    }

    public void setPlants(Plants plants) {
        this.plants = plants;
    }

    public static class Builder {
        private int stableCost; // current cost
        private int maxCost;
        private int minCost;
        private int regionCost; // mean
        private Date dateUpdateCost;
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
        public Builder dateUpdateCost(Date dateUpdateCost) {
            this.dateUpdateCost = dateUpdateCost;
            return this;
        }
        public CostPlant build() {
            return new CostPlant(this);
        }
    }
}
