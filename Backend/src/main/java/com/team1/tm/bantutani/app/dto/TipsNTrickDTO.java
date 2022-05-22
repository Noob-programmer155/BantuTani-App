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
}
