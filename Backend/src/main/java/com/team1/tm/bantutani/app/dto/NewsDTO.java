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
    private List<String> keywordsResponse;
    private List<String> keywordsRequest;
    private List<MultipartFile> images;
    private List<String> imagesResponse;
    private String video;

    public NewsDTO() {
    }

    public NewsDTO(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.descriptionSummary = builder.descriptionSummary;
        this.date = builder.date;
        this.source = builder.source;
        this.keywordsResponse = builder.keywordsResponse;
        this.imagesResponse = builder.imagesResponse;
        this.video = builder.video;
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

    public List<String> getKeywordsResponse() {
        return keywordsResponse;
    }

    public void setKeywordsResponse(List<String> keywordsResponse) {
        this.keywordsResponse = keywordsResponse;
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

    public List<String> getImagesResponse() {
        return imagesResponse;
    }

    public void setImagesResponse(List<String> imagesResponse) {
        this.imagesResponse = imagesResponse;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private String descriptionSummary;
        private Date date;
        private String source;
        private List<String> keywordsResponse;
        private List<String> imagesResponse;
        private String video;
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder title(String title) {
            this.title = title;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder descriptionSummary(String descriptionSummary) {
            this.descriptionSummary = descriptionSummary;
            return this;
        }
        public Builder date(Date date) {
            this.date = date;
            return this;
        }
        public Builder source(String source) {
            this.source = source;
            return this;
        }
        public Builder keywordsResponse(List<String> keywordsResponse) {
            this.keywordsResponse = keywordsResponse;
            return this;
        }
        public Builder imagesResponse(List<String> imagesResponse) {
            this.imagesResponse = imagesResponse;
            return this;
        }
        public Builder video(String video) {
            this.video = video;
            return this;
        }
        public NewsDTO build() {
            return new NewsDTO(this);
        }
    }
}
