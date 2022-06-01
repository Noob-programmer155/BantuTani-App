package com.team1.tm.bantutani.app.dto.response;

import java.sql.Date;

public class PlantsResponseMinDTO {
    private Long id;
    private String name;
    private String plantTypeImpl;
    private String image;
    private Date dateUpdate;

    public PlantsResponseMinDTO() {
    }

    public PlantsResponseMinDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.plantTypeImpl = builder.plantTypeImpl;
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

    public String getPlantTypeImpl() {
        return plantTypeImpl;
    }

    public void setPlantTypeImpl(String plantTypeImpl) {
        this.plantTypeImpl = plantTypeImpl;
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
        private String plantTypeImpl;
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
            this.plantTypeImpl = type;
            return this;
        }
        public Builder image(String image) {
            this.image = image;
            return this;
        }
        public Builder date(Date date) {
            this.dateUpdate = date;
            return this;
        }
        public PlantsResponseMinDTO build() {
            return new PlantsResponseMinDTO(this);
        }
    }
}
