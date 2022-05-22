package com.team1.tm.bantutani.app.dto.response;

import com.team1.tm.bantutani.app.dto.NewsDTO;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

public class NewsResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String descriptionSummary;
    private Date date;
    private String source;
    private List<String> keywords;
    private List<String> images;
    private String video;

    public NewsResponseDTO() {
    }

    public NewsResponseDTO(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.descriptionSummary = builder.descriptionSummary;
        this.date = builder.date;
        this.source = builder.source;
        this.keywords = builder.keywords;
        this.images = builder.images;
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

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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
        private List<String> keywords;
        private List<String> images;
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
        public Builder keywords(List<String> keywords) {
            this.keywords = keywords;
            return this;
        }
        public Builder images(List<String> images) {
            this.images = images;
            return this;
        }
        public Builder video(String video) {
            this.video = video;
            return this;
        }
        public NewsResponseDTO build() {
            return new NewsResponseDTO(this);
        }
    }
}
