package com.tms.casino.service;

import com.tms.casino.dto.BetRequest;
import com.tms.casino.model.Bet;
import com.tms.casino.model.Game;
import com.tms.casino.model.User;
import com.tms.casino.repository.BetRepository;
import com.tms.casino.repository.GameRepository;
import com.tms.casino.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BetService {

    private final BetRepository betRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final GameEngineService gameEngineService;

    @Transactional
    public Bet placeBet(String username, BetRequest betRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Game game = gameRepository.findById(betRequest.getGameId())
                .orElseThrow(() -> new RuntimeException("Game not found"));

        if (user.getBalance().compareTo(betRequest.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        // Создаем ставку
        Bet bet = new Bet();
        bet.setUser(user);
        bet.setGame(game);
        bet.setAmount(betRequest.getAmount());
        bet.setCreatedAt(LocalDateTime.now());

        // Обрабатываем ставку через игровой движок
        BigDecimal payout = gameEngineService.processBet(game, betRequest);
        bet.setPayout(payout);

        // Обновляем баланс пользователя
        user.setBalance(user.getBalance().subtract(betRequest.getAmount()).add(payout));
        userRepository.save(user);

        return betRepository.save(bet);
    }

    public List<Bet> getUserBetHistory(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return betRepository.findByUser(user);
    }
}