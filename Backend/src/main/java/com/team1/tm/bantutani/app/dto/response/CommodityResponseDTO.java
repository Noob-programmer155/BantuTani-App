package com.team1.tm.bantutani.app.dto.response;

public class CommodityResponseDTO {
    private int currentCost;
    private int previousCost;
    private String name;
    private String icon;
    private Boolean costIncrease;

    public CommodityResponseDTO() {
    }

    public CommodityResponseDTO(Builder builder) {
        this.currentCost = builder.currentCost;
        this.previousCost = builder.previousCost;
        this.name = builder.name;
        this.icon = builder.icon;
        this.costIncrease = builder.costIncrease;
    }

    public int getCurrentCost() {
        return currentCost;
    }

    public void setCurrentCost(int currentCost) {
        this.currentCost = currentCost;
    }

    public int getPreviousCost() {
        return previousCost;
    }

    public void setPreviousCost(int previousCost) {
        this.previousCost = previousCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getCostIncrease() {
        return costIncrease;
    }

    public void setCostIncrease(Boolean costIncrease) {
        this.costIncrease = costIncrease;
    }

    public static class Builder {
        private int currentCost;
        private int previousCost;
        private String name;
        private String icon;
        private Boolean costIncrease;
        public Builder currentCost(int cost) {
            this.currentCost = cost;
            return this;
        }
        public Builder previousCost(int cost) {
            this.previousCost = cost;
            return this;
        }
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder icon(String icon) {
            this.icon = icon;
            return this;
        }
        public Builder isIncrease(Boolean increase) {
            this.costIncrease = increase;
            return this;
        }
        public CommodityResponseDTO build() {
            return new CommodityResponseDTO(this);
        }
    }
}
