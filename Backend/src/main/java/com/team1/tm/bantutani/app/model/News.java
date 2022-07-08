package com.team1.tm.bantutani.app.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
public class News {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @Column(length = 5000000)
    private String descriptions;
    @Column(length = 15000)
    private String descriptionSummary;
    private Date dates;
    private Date dateUpdate;
    private String sources;
    @ManyToMany(mappedBy = "newsListTags", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<NewsTags> keywords = new LinkedHashSet<>();
    @ElementCollection
    private List<String> images = new LinkedList<>();
    private String video;

    public News() {
    }

    public News(Builder build) {
        this.title = build.title;
        this.descriptions = build.description;
        this.descriptionSummary = build.descriptionSummary;
        this.dates = build.date;
        this.dateUpdate = build.dateUpdate;
        this.sources = build.source;
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

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String description) {
        this.descriptions = description;
    }

    public String getDescriptionSummary() {
        return descriptionSummary;
    }

    public void setDescriptionSummary(String descriptionSummary) {
        this.descriptionSummary = descriptionSummary;
    }

    public Date getDates() {
        return dates;
    }

    public void setDates(Date date) {
        this.dates = date;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String source) {
        this.sources = source;
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

    public Set<NewsTags> getKeywords() {
        return keywords;
    }

    public void addTag(NewsTags newsTags) {
        if(this.keywords.contains(newsTags)) return ;
        this.keywords.add(newsTags);
        newsTags.addNews(this);
    }
    public void removeTag(NewsTags newsTags) {
        if(!this.keywords.contains(newsTags)) return ;
        this.keywords.remove(newsTags);
        newsTags.removeNews(this);
    }

    public static class Builder{
        private String title;
        private String description;
        private String descriptionSummary;
        private Date date;
        private Date dateUpdate;
        private String source;
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
        public Builder dateUpdate(Date date) {
            this.dateUpdate = date;
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
