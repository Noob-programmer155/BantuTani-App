package com.team1.tm.bantutani.app.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
public class NewsTags {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true,nullable = false)
    private String name;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "NewsListTagsTable")
    private Set<News> newsListTags = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<News> getNewsListTags() {
        return newsListTags;
    }

    public void addNews(News news) {
        if(this.newsListTags.contains(news)) return ;
        this.newsListTags.add(news);
        news.addTag(this);
    }
    public void removeNews(News news) {
        if(!this.newsListTags.contains(news)) return ;
        this.newsListTags.remove(news);
        news.removeTag(this);
    }
}
