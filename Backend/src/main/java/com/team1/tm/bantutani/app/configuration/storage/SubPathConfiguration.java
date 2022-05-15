package com.team1.tm.bantutani.app.configuration.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(value = "subdirectory")
public class SubPathConfiguration {
    private String news;
    private String plants;
    private String planting;
    private String diseases;
    private String weeds;
    private String pests;
    private String care;
    private String user;

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getPlants() {
        return plants;
    }

    public void setPlants(String plants) {
        this.plants = plants;
    }

    public String getPlanting() {
        return planting;
    }

    public void setPlanting(String planting) {
        this.planting = planting;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public String getWeeds() {
        return weeds;
    }

    public void setWeeds(String weeds) {
        this.weeds = weeds;
    }

    public String getPests() {
        return pests;
    }

    public void setPests(String pests) {
        this.pests = pests;
    }

    public String getCare() {
        return care;
    }

    public void setCare(String care) {
        this.care = care;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
