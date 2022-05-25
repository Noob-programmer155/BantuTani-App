package com.team1.tm.bantutani.app.dto.response;

public class PlantTypeResponseDTO {
    private Long id;
    private String type;
    private Boolean icon;

    public PlantTypeResponseDTO() {
    }

    public PlantTypeResponseDTO(Builder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.icon = builder.icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIcon() {
        return icon;
    }

    public void setIcon(Boolean icon) {
        this.icon = icon;
    }

    public static class Builder {
        private Long id;
        private String type;
        private Boolean icon;
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder type(String type) {
            this.type = type;
            return this;
        }
        public Builder isIconAlready(Boolean icon) {
            this.icon = icon;
            return this;
        }
        public PlantTypeResponseDTO build() {
            return new PlantTypeResponseDTO(this);
        }
    }
}
