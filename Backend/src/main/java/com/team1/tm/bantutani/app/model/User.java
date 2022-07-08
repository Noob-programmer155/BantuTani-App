package com.team1.tm.bantutani.app.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.team1.tm.bantutani.app.model.other.Provider;
import com.team1.tm.bantutani.app.model.other.Status;
import com.team1.tm.bantutani.app.model.plants.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.LinkedList;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty
    @Column(unique = true)
    private String username;
//    @NotEmpty
    private String fullName;
    @JsonIgnore
    private String password;
    @Email
    private String email;
    @NotEmpty
    private String image;
    @Enumerated
    private Provider provider;
    private String clientId;
    @Enumerated
    private Status status;
    private Boolean disable;
    @OneToMany(mappedBy = "authorPlantsCare", cascade = CascadeType.ALL)
    private List<PlantsCare> care = new LinkedList<>();
    @OneToMany(mappedBy = "authorPlantsPlanting", cascade = CascadeType.ALL)
    private List<PlantsPlanting> plantings = new LinkedList<>();
    @OneToMany(mappedBy = "authorPlantsAttribute", cascade = CascadeType.ALL)
    private List<PlantsDisease> diseases = new LinkedList<>();
    @OneToMany(mappedBy = "authorPlantsWeeds", cascade = CascadeType.ALL)
    private List<PlantsWeeds> weeds = new LinkedList<>();
    @OneToMany(mappedBy = "authorPlantsPest", cascade = CascadeType.ALL)
    private List<PlantsPest> pests = new LinkedList<>();
    @OneToMany(mappedBy = "authorTipsNTrick", cascade = CascadeType.ALL)
    private List<TipsNTrick> tipsNTricks = new LinkedList<>();

    public User() {
    }

    public User(Builder builder) {
        this.username = builder.username;
        this.fullName = builder.fullName;
        this.password = builder.password;
        this.email = builder.email;
        this.image = builder.image;
        this.provider = builder.provider;
        this.clientId = builder.clientId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean isDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public List<PlantsCare> getCare() {
        return care;
    }

    public void setCare(List<PlantsCare> care) {
        this.care = care;
    }

    public List<PlantsPlanting> getPlantings() {
        return plantings;
    }

    public void setPlantings(List<PlantsPlanting> plantings) {
        this.plantings = plantings;
    }

    public List<PlantsDisease> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<PlantsDisease> diseases) {
        this.diseases = diseases;
    }

    public List<PlantsWeeds> getWeeds() {
        return weeds;
    }

    public void setWeeds(List<PlantsWeeds> weeds) {
        this.weeds = weeds;
    }

    public List<PlantsPest> getPests() {
        return pests;
    }

    public void setPests(List<PlantsPest> pests) {
        this.pests = pests;
    }

    public List<TipsNTrick> getTipsNTricks() {
        return tipsNTricks;
    }

    public void setTipsNTricks(List<TipsNTrick> tipsNTricks) {
        this.tipsNTricks = tipsNTricks;
    }

    public static class Builder {
        private String username;
        private String fullName;
        private String password;
        private String email;
        private String image;
        private Provider provider;
        private String clientId;
        private Status status;
        public Builder username(String username) {
            this.username = username;
            return this;
        }
        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }
        public Builder password(String password) {
            this.password = password;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder image(String image) {
            this.image = image;
            return this;
        }
        public Builder provider(Provider provider) {
            this.provider = provider;
            return this;
        }
        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }
        public Builder status(Status status) {
            this.status = status;
            return this;
        }
        public User build() {
            return new User(this);
        }
    }
}
