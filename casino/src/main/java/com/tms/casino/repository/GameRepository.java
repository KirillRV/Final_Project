package com.tms.casino.repository;

import com.tms.casino.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByIsActive(boolean isActive);
}