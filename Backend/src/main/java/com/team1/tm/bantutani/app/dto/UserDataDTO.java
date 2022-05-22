package com.team1.tm.bantutani.app.dto;
import com.team1.tm.bantutani.app.dto.response.*;

import java.util.List;

public class UserDataDTO {
    private List<PlantsResponseMinDTO> care;
    private List<PlantsResponseMinDTO> planting;
    private List<PlantAttributeResponseMinDTO> diseases;
    private List<PlantAttributeResponseMinDTO> weeds;
    private List<PlantAttributeResponseMinDTO> pests;

    public UserDataDTO() {
    }

    public UserDataDTO(Builder builder) {
        this.planting = builder.planting;
        this.diseases = builder.diseases;
        this.weeds = builder.weeds;
        this.pests = builder.pests;
        this.care = builder.care;
    }

    public List<PlantsResponseMinDTO> getCare() {
        return care;
    }

    public void setCare(List<PlantsResponseMinDTO> care) {
        this.care = care;
    }

    public List<PlantsResponseMinDTO> getPlanting() {
        return planting;
    }

    public void setPlanting(List<PlantsResponseMinDTO> planting) {
        this.planting = planting;
    }

    public List<PlantAttributeResponseMinDTO> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<PlantAttributeResponseMinDTO> diseases) {
        this.diseases = diseases;
    }

    public List<PlantAttributeResponseMinDTO> getWeeds() {
        return weeds;
    }

    public void setWeeds(List<PlantAttributeResponseMinDTO> weeds) {
        this.weeds = weeds;
    }

    public List<PlantAttributeResponseMinDTO> getPests() {
        return pests;
    }

    public void setPests(List<PlantAttributeResponseMinDTO> pests) {
        this.pests = pests;
    }

    public static class Builder {
        private List<PlantsResponseMinDTO> care;
        private List<PlantsResponseMinDTO> planting;
        private List<PlantAttributeResponseMinDTO> diseases;
        private List<PlantAttributeResponseMinDTO> weeds;
        private List<PlantAttributeResponseMinDTO> pests;

        public Builder care(List<PlantsResponseMinDTO> care) {
            this.care = care;
            return this;
        }
        public Builder planting(List<PlantsResponseMinDTO> planting) {
            this.planting = planting;
            return this;
        }
        public Builder diseases(List<PlantAttributeResponseMinDTO> diseases) {
            this.diseases = diseases;
            return this;
        }
        public Builder weeds(List<PlantAttributeResponseMinDTO> weeds) {
            this.weeds = weeds;
            return this;
        }
        public Builder pests(List<PlantAttributeResponseMinDTO> pests) {
            this.pests = pests;
            return this;
        }
        public UserDataDTO build() {
            return new UserDataDTO(this);
        }
    }
}
