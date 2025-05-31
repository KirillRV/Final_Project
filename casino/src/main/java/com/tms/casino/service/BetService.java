package com.tms.casino.service;

import com.tms.casino.exception.EntityNotFoundException;
import com.tms.casino.exception.GameNotActiveException;
import com.tms.casino.exception.InsufficientFundsException;
import com.tms.casino.exception.InvalidBetException;
import com.tms.casino.model.dto.BetRequest;
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
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Game game = gameRepository.findById(betRequest.getGameId())
                .orElseThrow(() -> new EntityNotFoundException("Game not found"));

        // Проверка активности игры
        if (!game.isActive()) {
            throw new GameNotActiveException(game.getGameId());
        }

        // Проверка минимальной/максимальной ставки
        if (betRequest.getAmount().compareTo(game.getMinBet()) < 0) {
            throw new InvalidBetException("Bet amount below minimum: " + game.getMinBet());
        }

        if (betRequest.getAmount().compareTo(game.getMaxBet()) > 0) {
            throw new InvalidBetException("Bet amount exceeds maximum: " + game.getMaxBet());
        }

        // Проверка на отрицательную ставку
        if (betRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidBetException("Bet amount must be positive");
        }

        // Проверка баланса
        if (user.getBalance().compareTo(betRequest.getAmount()) < 0) {
            throw new InsufficientFundsException();
        }

        // Создаем ставку
        Bet bet = new Bet();
        bet.setUser(user);
        bet.setGame(game);
        bet.setAmount(betRequest.getAmount());
        bet.setCreatedAt(LocalDateTime.now());

        // Обработка ставки
        BigDecimal payout = gameEngineService.processBet(game, betRequest);
        bet.setPayout(payout);

        // Обновление баланса
        BigDecimal newBalance = user.getBalance()
                .subtract(betRequest.getAmount())
                .add(payout);
        user.setBalance(newBalance);
        userRepository.save(user);

        return betRepository.save(bet);
    }

    public List<Bet> getUserBetHistory(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return betRepository.findByUser(user);
    }
}