package com.tms.casino.repository;

import com.tms.casino.model.Bet;
import com.tms.casino.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<Bet, Integer> {

    List<Bet> findByUserOrderByCreatedAtDesc(User user);

}