package com.team1.tm.bantutani.app.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
public class News {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @Column(length=5000000)
    private String description;
    private String descriptionSummary;
    private Date date;
    private String source;
    @ElementCollection
    private List<String> keywords = new LinkedList<>();
    @ElementCollection
    private List<String> images = new LinkedList<>();
    private String video;

    public News() {
    }

    public News(Builder build) {
        this.title = build.title;
        this.description = build.description;
        this.descriptionSummary = build.descriptionSummary;
        this.date = build.date;
        this.source = build.source;
        this.keywords = build.keywords;
        this.images = build.images;
        this.video = build.video;
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

    public static class Builder{
        private String title;
        private String description;
        private String descriptionSummary;
        private Date date;
        private String source;
        private List<String> keywords = new LinkedList<>();
        private List<String> images = new LinkedList<>();
        private String video;

        public Builder() {
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
        public News build(){
            return new News(this);
        }
    }
}
