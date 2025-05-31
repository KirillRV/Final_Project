package com.tms.casino.repository;

import com.tms.casino.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByIsActive(boolean isActive);
    List<Game> findByCategory(String category);
    List<Game> findByProvider(String provider);
    Optional<Game> findBySlug(String slug);
}