package com.team1.tm.bantutani.app.dto.response;

public class PlantAttributeResponseDetectionIDDTO {
    private Long id;
    private String name;

    public PlantAttributeResponseDetectionIDDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
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

    public static class Builder {
        private Long id;
        private String name;
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public PlantAttributeResponseDetectionIDDTO build() {
            return new PlantAttributeResponseDetectionIDDTO(this);
        }
    }
}
