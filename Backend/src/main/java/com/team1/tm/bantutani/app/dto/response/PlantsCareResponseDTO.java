package com.team1.tm.bantutani.app.dto.response;

import com.team1.tm.bantutani.app.dto.PlantsCareDTO;
import com.team1.tm.bantutani.app.dto.TipsNTrickDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PlantsCareResponseDTO {
    private Long id;
    private String description;
    private String careType;
    private String animation;
    private String image;
    private String video;
    private List<TipsNTrickResponseDTO> tipsNTrickDTOList;
    private String authorPlantsCare;

    public PlantsCareResponseDTO() {
    }

    public PlantsCareResponseDTO(Builder builder) {
        this.id = builder.id;
        this.description = builder.description;
        this.careType = builder.careType;
        this.animation = builder.animation;
        this.image = builder.image;
        this.video = builder.video;
        this.authorPlantsCare = builder.authorPlantsCare;
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

    public List<TipsNTrickResponseDTO> getTipsNTrickDTOList() {
        return tipsNTrickDTOList;
    }

    public void setTipsNTrickDTOList(List<TipsNTrickResponseDTO> tipsNTrickDTOList) {
        this.tipsNTrickDTOList = tipsNTrickDTOList;
    }

    public String getAnimation() {
        return animation;
    }

    public void setAnimation(String animation) {
        this.animation = animation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getAuthorPlantsCare() {
        return authorPlantsCare;
    }

    public void setAuthorPlantsCare(String authorPlantsCare) {
        this.authorPlantsCare = authorPlantsCare;
    }

    public static class Builder {
        private Long id;
        private String description;
        private String careType;
        private String animation;
        private String image;
        private String video;
        private String authorPlantsCare;
        private List<TipsNTrickResponseDTO> tipsNTrickDTOList;
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
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
        public Builder listTipsNTrick(List<TipsNTrickResponseDTO> tipsNTrickDTOList){
            this.tipsNTrickDTOList = tipsNTrickDTOList;
            return this;
        }
        public PlantsCareResponseDTO build() {
            return new PlantsCareResponseDTO(this);
        }
    }
}
