package com.team1.tm.bantutani.app.dto.response;

import com.team1.tm.bantutani.app.dto.UserDTO;

public class UserResponseDTO {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String image;
    private String status;

    public UserResponseDTO() {
    }

    public UserResponseDTO(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.fullName = builder.fullName;
        this.email = builder.email;
        this.image = builder.image;
        this.status = builder.status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Builder {
        private Long id;
        private String username;
        private String fullName;
        private String email;
        private String image;
        private String status;
        public Builder id(Long id){
            this.id = id;
            return this;
        }
        public Builder username(String username){
            this.username = username;
            return this;
        }
        public Builder fullName(String fullName){
            this.fullName = fullName;
            return this;
        }
        public Builder email(String email){
            this.email = email;
            return this;
        }
        public Builder image(String image){
            this.image = image;
            return this;
        }
        public Builder status(String status){
            this.status = status;
            return this;
        }
        public UserResponseDTO build(){
            return new UserResponseDTO(this);
        }
    }
}
