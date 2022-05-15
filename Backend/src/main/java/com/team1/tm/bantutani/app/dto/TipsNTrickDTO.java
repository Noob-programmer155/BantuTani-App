package com.team1.tm.bantutani.app.dto;

public class TipsNTrickDTO {
    private Long id;
    private String name;
    private String description;
    private String animation;
    private String typeStep;
    private String typeActivity;
    private Long authorTipsNTrick;
    private Long plantsCareTips;
    private Long plantsPlantingTips;

    public TipsNTrickDTO() {
    }

    public TipsNTrickDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.animation = builder.animation;
        this.typeStep = builder.typeStep;
        this.typeActivity = builder.typeActivity;
        this.authorTipsNTrick = builder.authorTipsNTrick;
        this.plantsCareTips = builder.plantsCareTips;
        this.plantsPlantingTips = builder.plantsPlantingTips;
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

    public String getAnimation() {
        return animation;
    }

    public void setAnimation(String animation) {
        this.animation = animation;
    }

    public String getTypeStep() {
        return typeStep;
    }

    public void setTypeStep(String typeStep) {
        this.typeStep = typeStep;
    }

    public String getTypeActivity() {
        return typeActivity;
    }

    public void setTypeActivity(String typeActivity) {
        this.typeActivity = typeActivity;
    }

    public Long getAuthorTipsNTrick() {
        return authorTipsNTrick;
    }

    public void setAuthorTipsNTrick(Long authorTipsNTrick) {
        this.authorTipsNTrick = authorTipsNTrick;
    }

    public Long getPlantsCareTips() {
        return plantsCareTips;
    }

    public void setPlantsCareTips(Long plantsCareTips) {
        this.plantsCareTips = plantsCareTips;
    }

    public Long getPlantsPlantingTips() {
        return plantsPlantingTips;
    }

    public void setPlantsPlantingTips(Long plantsPlantingTips) {
        this.plantsPlantingTips = plantsPlantingTips;
    }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private String animation;
        private String typeStep;
        private String typeActivity;
        private Long authorTipsNTrick;
        private Long plantsCareTips;
        private Long plantsPlantingTips;
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
        public Builder animation(String animation) {
            this.animation = animation;
            return this;
        }
        public Builder step(String typeStep) {
            this.typeStep = typeStep;
            return this;
        }
        public Builder activity(String typeActivity) {
            this.typeActivity = typeActivity;
            return this;
        }
        public Builder author(Long authorTipsNTrick) {
            this.authorTipsNTrick = authorTipsNTrick;
            return this;
        }
        public Builder plantCare(Long plantCare) {
            this.plantsCareTips = plantCare;
            return this;
        }
        public Builder plantPlanting(Long plantPlanting) {
            this.plantsPlantingTips = plantPlanting;
            return this;
        }
        public TipsNTrickDTO build() {
            return new TipsNTrickDTO(this);
        }
    }
}
