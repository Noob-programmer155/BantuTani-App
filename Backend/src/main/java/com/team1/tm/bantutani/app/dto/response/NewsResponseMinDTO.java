package com.team1.tm.bantutani.app.dto.response;

import java.sql.Date;
import java.util.List;

public class NewsResponseMinDTO {
    private Long id;
    private String title;
    private Date dateUpdate;
    private Date date;
    private String image;
    private String video;

    public NewsResponseMinDTO() {
    }

    public NewsResponseMinDTO(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.dateUpdate = builder.dateUpdate;
        this.date = builder.date;
        this.image = builder.image;
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

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
        private Date dateUpdate;
        private Date date;
        private String image;
        private String video;
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder title(String title) {
            this.title = title;
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
        public Builder images(String image) {
            this.image = image;
            return this;
        }
        public Builder video(String video) {
            this.video = video;
            return this;
        }
        public NewsResponseMinDTO build() {
            return new NewsResponseMinDTO(this);
        }
    }
}
