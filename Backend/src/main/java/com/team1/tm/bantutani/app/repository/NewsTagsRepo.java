package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.NewsTags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsTagsRepo extends JpaRepository<NewsTags, Long> {
    public boolean existsByName(String name);
    public Optional<NewsTags> findByName(String name);
    public Page<NewsTags> findByNameContaining(String name, Pageable pageable);
}
