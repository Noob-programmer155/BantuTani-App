package com.team1.tm.bantutani.app.dto.response;

import com.team1.tm.bantutani.app.dto.PlantsPlantingDTO;
import com.team1.tm.bantutani.app.dto.TipsNTrickDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PlantsPlantingResponseDTO {
    private Long id;
    private String description;
    private String animation;
    private String image;
    private String video;
    private String authorPlantsPlanting;
    private List<TipsNTrickResponseDTO> tipsNTricks;

    public PlantsPlantingResponseDTO() {
    }

    public PlantsPlantingResponseDTO(Builder builder) {
        this.id = builder.id;
        this.description = builder.description;
        this.animation = builder.animation;
        this.image = builder.image;
        this.video = builder.video;
        this.authorPlantsPlanting = builder.authorPlantsPlantingResponse;
        this.tipsNTricks = builder.tipsNTricks;
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

    public String getAnimation() {
        return animation;
    }

    public void setAnimation(String animation) {
        this.animation = animation;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthorPlantsPlanting() {
        return authorPlantsPlanting;
    }

    public void setAuthorPlantsPlanting(String authorPlantsPlanting) {
        this.authorPlantsPlanting = authorPlantsPlanting;
    }

    public List<TipsNTrickResponseDTO> getTipsNTricks() {
        return tipsNTricks;
    }

    public void setTipsNTricks(List<TipsNTrickResponseDTO> tipsNTricks) {
        this.tipsNTricks = tipsNTricks;
    }

    public static class Builder {
        private Long id;
        private String description;
        private String animation;
        private String image;
        private String video;
        private String authorPlantsPlantingResponse;
        private List<TipsNTrickResponseDTO> tipsNTricks;
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
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
        public Builder tipsNTricks(List<TipsNTrickResponseDTO> tipsNTricks) {
            this.tipsNTricks = tipsNTricks;
            return this;
        }
        public Builder author(String author) {
            this.authorPlantsPlantingResponse = author;
            return this;
        }
        public PlantsPlantingResponseDTO build() {
            return new PlantsPlantingResponseDTO(this);
        }
    }
}
