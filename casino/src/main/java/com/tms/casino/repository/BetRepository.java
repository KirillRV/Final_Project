package com.tms.casino.repository;

import com.tms.casino.model.Bet;
import com.tms.casino.model.Game;
import com.tms.casino.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {
    List<Bet> findByUser(User user);
    List<Bet> findByUserAndGame(User user, Game game);
    List<Bet> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}