package com.team1.tm.bantutani.app.model;

import com.team1.tm.bantutani.app.model.other.AnimationType;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Animation {
    @Id
    @GeneratedValue
    private Long id;
    private String filename;
    @Enumerated
    private AnimationType animationType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public AnimationType getAnimationType() {
        return animationType;
    }

    public void setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
    }
}
