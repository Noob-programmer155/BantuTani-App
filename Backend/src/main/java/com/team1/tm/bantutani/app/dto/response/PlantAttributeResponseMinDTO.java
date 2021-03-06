package com.team1.tm.bantutani.app.dto.response;

import java.sql.Date;
import java.util.List;

public class PlantAttributeResponseMinDTO {
    private Long id;
    private String name;
    private String attributePlantsType;
    private String image;
    private Date dateUpdate;

    public PlantAttributeResponseMinDTO() {
    }

    public PlantAttributeResponseMinDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.attributePlantsType = builder.plantType;
        this.image = builder.image;
        this.dateUpdate = builder.dateUpdate;
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

    public String getAttributePlantsType() {
        return attributePlantsType;
    }

    public void setAttributePlantsType(String attributePlantsType) {
        this.attributePlantsType = attributePlantsType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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
        private String plantType;
        private String image;
        private Date dateUpdate;
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder type(String type) {
            this.plantType = type;
            return this;
        }
        public Builder image(String image) {
            this.image = image;
            return this;
        }
        public Builder dateUpdate(Date date) {
            this.dateUpdate = date;
            return this;
        }
        public PlantAttributeResponseMinDTO build() {
            return new PlantAttributeResponseMinDTO(this);
        }
    }
}
