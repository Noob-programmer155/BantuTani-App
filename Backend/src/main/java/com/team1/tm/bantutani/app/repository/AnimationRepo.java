package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.Animation;
import com.team1.tm.bantutani.app.model.other.AnimationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnimationRepo extends JpaRepository<Animation,Long> {
    public Optional<Animation> findByFilename(String filename);
    public Page<Animation> findByAnimationType(AnimationType animationType, Pageable pageable);
}
