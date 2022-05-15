package com.team1.tm.bantutani.app.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PlantsCareDTO {
    private Long id;
    private String description;
    private String careType;
    private String animation;
    private MultipartFile image;
    private String imageResponse;
    private String video;
    private Long authorPlantsCare;
    private List<TipsNTrickDTO> tipsNTrickDTOList;
    private String authorPlantsCareResponse;
    private Long plantsDiseaseCare;
    private Long plantsWeedsCare;
    private Long plantsPestCare;
    private Long plants;

    public PlantsCareDTO(Builder builder) {
        this.description = builder.description;
        this.careType = builder.careType;
        this.animation = builder.animation;
        this.imageResponse = builder.image;
        this.video = builder.video;
        this.authorPlantsCareResponse = builder.authorPlantsCare;
        this.tipsNTrickDTOList = builder.tipsNTrickDTOList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getCareType() {
        return careType;
    }

    public void setCareType(String careType) {
        this.careType = careType;
    }

    public String getImageResponse() {
        return imageResponse;
    }

    public void setImageResponse(String imageResponse) {
        this.imageResponse = imageResponse;
    }

    public String getAuthorPlantsCareResponse() {
        return authorPlantsCareResponse;
    }

    public void setAuthorPlantsCareResponse(String authorPlantsCareResponse) {
        this.authorPlantsCareResponse = authorPlantsCareResponse;
    }

    public List<TipsNTrickDTO> getTipsNTrickDTOList() {
        return tipsNTrickDTOList;
    }

    public void setTipsNTrickDTOList(List<TipsNTrickDTO> tipsNTrickDTOList) {
        this.tipsNTrickDTOList = tipsNTrickDTOList;
    }

    public String getAnimation() {
        return animation;
    }

    public void setAnimation(String animation) {
        this.animation = animation;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Long getAuthorPlantsCare() {
        return authorPlantsCare;
    }

    public void setAuthorPlantsCare(Long authorPlantsCare) {
        this.authorPlantsCare = authorPlantsCare;
    }

    public Long getPlantsDiseaseCare() {
        return plantsDiseaseCare;
    }

    public void setPlantsDiseaseCare(Long plantsDiseaseCare) {
        this.plantsDiseaseCare = plantsDiseaseCare;
    }

    public Long getPlantsWeedsCare() {
        return plantsWeedsCare;
    }

    public void setPlantsWeedsCare(Long plantsWeedsCare) {
        this.plantsWeedsCare = plantsWeedsCare;
    }

    public Long getPlantsPestCare() {
        return plantsPestCare;
    }

    public void setPlantsPestCare(Long plantsPestCare) {
        this.plantsPestCare = plantsPestCare;
    }

    public Long getPlants() {
        return plants;
    }

    public void setPlants(Long plants) {
        this.plants = plants;
    }

    public static class Builder {
        private String description;
        private String careType;
        private String animation;
        private String image;
        private String video;
        private String authorPlantsCare;
        private List<TipsNTrickDTO> tipsNTrickDTOList;
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder careType(String careType) {
            this.careType = careType;
            return this;
        }
        public Builder animation(String animation) {
            this.animation = animation;
            return this;
        }
        public Builder image(String image) {
            this.image = image;
            return this;
        }
        public Builder video(String video) {
            this.video = video;
            return this;
        }
        public Builder author(String author) {
            this.authorPlantsCare = author;
            return this;
        }
        public Builder listTipsNTrick(List<TipsNTrickDTO> tipsNTrickDTOList){
            this.tipsNTrickDTOList = tipsNTrickDTOList;
            return this;
        }
        public PlantsCareDTO build() {
            return new PlantsCareDTO(this);
        }
    }
}
