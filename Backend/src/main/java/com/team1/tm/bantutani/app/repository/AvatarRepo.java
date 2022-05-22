package com.team1.tm.bantutani.app.repository;

import com.team1.tm.bantutani.app.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvatarRepo extends JpaRepository<Avatar, Long> {
    public Optional<Avatar> findByName(String name);
}
