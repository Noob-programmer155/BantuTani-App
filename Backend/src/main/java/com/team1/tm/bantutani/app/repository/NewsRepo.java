package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.News;
import com.team1.tm.bantutani.app.model.NewsTitle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface NewsRepo extends JpaRepository<News, Long> {
    public Page<News> findByDatesBetween(Date start, Date end,Pageable pageable);
    public Page<News> findDistinctByTitleContainingOrKeywordsName(String title, String keywordsName, Pageable pageable);
    public Page<NewsTitle> findAllDistinctByTitleContainingOrKeywordsName(String title, String keywordsName, Pageable pageable);
}
