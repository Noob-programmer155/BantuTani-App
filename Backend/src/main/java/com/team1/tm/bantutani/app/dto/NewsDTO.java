package com.team1.tm.bantutani.app.dto;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

public class NewsDTO {
    private Long id;
    private String title;
    private String description;
    private String descriptionSummary;
    private Date date;
    private String source;
    private List<Long> keywords;
    private List<String> keywordsRequest;
    private List<MultipartFile> images;
    private String video;

    public NewsDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionSummary() {
        return descriptionSummary;
    }

    public void setDescriptionSummary(String descriptionSummary) {
        this.descriptionSummary = descriptionSummary;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<Long> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Long> keywords) {
        this.keywords = keywords;
    }

    public List<String> getKeywordsRequest() {
        return keywordsRequest;
    }

    public void setKeywordsRequest(List<String> keywordsRequest) {
        this.keywordsRequest = keywordsRequest;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
