package com.tms.casino.repository;

import com.tms.casino.model.Bonus;
import com.tms.casino.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {
    List<Bonus> findByUser(User user);
    List<Bonus> findByUserAndStatus(User user, Bonus.BonusStatus status);
    List<Bonus> findByExpiresAtBeforeAndStatus(LocalDateTime date, Bonus.BonusStatus status);
}