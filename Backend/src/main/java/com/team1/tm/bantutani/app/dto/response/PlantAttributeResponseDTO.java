package com.team1.tm.bantutani.app.dto.response;

import com.team1.tm.bantutani.app.dto.PlantAttributeDTO;
import com.team1.tm.bantutani.app.dto.PlantsCareDTO;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

public class PlantAttributeResponseDTO {
    private Long id;
    private String name;
    private String attributePlantsType;
    private List<String> otherNames;
    private String description;
    private List<String> image;
    private String authorPlantsAttribute;
    private Date dateUpdate;
    private List<PlantsCareResponseDTO> plantsCares;

    public PlantAttributeResponseDTO() {
    }

    public PlantAttributeResponseDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.attributePlantsType = builder.plantType;
        this.otherNames = builder.otherNames;
        this.description = builder.description;
        this.image = builder.image;
        this.authorPlantsAttribute = builder.authorPlantsAttributeResponse;
        this.plantsCares = builder.plantsCares;
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

    public List<PlantsCareResponseDTO> getPlantsCares() {
        return plantsCares;
    }

    public void setPlantsCares(List<PlantsCareResponseDTO> plantsCares) {
        this.plantsCares = plantsCares;
    }

    public String getAttributePlantsType() {
        return attributePlantsType;
    }

    public void setAttributePlantsType(String attributePlantsType) {
        this.attributePlantsType = attributePlantsType;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public String getAuthorPlantsAttribute() {
        return authorPlantsAttribute;
    }

    public void setAuthorPlantsAttribute(String authorPlantsAttribute) {
        this.authorPlantsAttribute = authorPlantsAttribute;
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
        private List<String> otherNames;
        private String description;
        private List<String> image;
        private String authorPlantsAttributeResponse;
        private List<PlantsCareResponseDTO> plantsCares;
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
        public Builder otherNames(List<String> otherNames) {
            this.otherNames = otherNames;
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
        public Builder author(String author) {
            this.authorPlantsAttributeResponse = author;
            return this;
        }
        public Builder care(List<PlantsCareResponseDTO> plantsCares) {
            this.plantsCares = plantsCares;
            return this;
        }
        public PlantAttributeResponseDTO build() {
            return new PlantAttributeResponseDTO(this);
        }
    }
}
