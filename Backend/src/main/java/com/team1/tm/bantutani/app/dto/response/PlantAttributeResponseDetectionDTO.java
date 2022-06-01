package com.team1.tm.bantutani.app.dto.response;

import java.sql.Date;
import java.util.List;

public class PlantAttributeResponseDetectionDTO {
    private Long id;
    private String name;
    private String description;
    private List<String> image;
    private Date dateUpdate;
    private List<PlantsCareResponseDTO> plantsCares;

    public PlantAttributeResponseDetectionDTO() {
    }

    public PlantAttributeResponseDetectionDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.dateUpdate = builder.dateUpdate;
        this.image = builder.image;
        this.plantsCares = builder.plantsCares;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PlantsCareResponseDTO> getPlantsCares() {
        return plantsCares;
    }

    public void setPlantsCares(List<PlantsCareResponseDTO> plantsCares) {
        this.plantsCares = plantsCares;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private List<String> image;
        private Date dateUpdate;
        private List<PlantsCareResponseDTO> plantsCares;
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder dateUpdate(Date date) {
            this.dateUpdate = date;
            return this;
        }
        public Builder image(List<String> image) {
            this.image = image;
            return this;
        }
        public Builder care(List<PlantsCareResponseDTO> plantsCares) {
            this.plantsCares = plantsCares;
            return this;
        }
        public PlantAttributeResponseDetectionDTO build() {
            return new PlantAttributeResponseDetectionDTO(this);
        }
    }
}
