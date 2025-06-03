package com.tms.casino.service;

import com.tms.casino.exception.CasinoRuntimeException;
import com.tms.casino.model.dto.BetRequest;
import com.tms.casino.model.Bet;
import com.tms.casino.model.Game;
import com.tms.casino.model.User;
import com.tms.casino.repository.BetRepository;
import com.tms.casino.repository.GameRepository;
import com.tms.casino.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
                .orElseThrow(() -> new CasinoRuntimeException(
                        "NOT_FOUND",
                        "User not found",
                        HttpStatus.NOT_FOUND
                ));

        Game game = gameRepository.findById(betRequest.getGameId())
                .orElseThrow(() -> new CasinoRuntimeException(
                        "NOT_FOUND",
                        "Game not found",
                        HttpStatus.NOT_FOUND
                ));

        // Проверка активности игры
        if (!game.isActive()) {
            throw new CasinoRuntimeException(
                    "GAME_NOT_ACTIVE",                // код ошибки
                    "Game with id " + game.getGameId() + " is not active",  // сообщение
                    HttpStatus.BAD_REQUEST            // HTTP статус
            );

        }

        if (betRequest.getAmount().compareTo(game.getMinBet()) < 0) {
            throw new CasinoRuntimeException(
                    "INVALID_BET",
                    "Bet amount below minimum: " + game.getMinBet(),
                    HttpStatus.BAD_REQUEST
            );
        }

        if (betRequest.getAmount().compareTo(game.getMaxBet()) > 0) {
            throw new CasinoRuntimeException(
                    "INVALID_BET",
                    "Bet amount exceeds maximum: " + game.getMaxBet(),
                    HttpStatus.BAD_REQUEST
            );
        }

// Проверка на отрицательную ставку
        if (betRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CasinoRuntimeException(
                    "INVALID_BET",
                    "Bet amount must be positive",
                    HttpStatus.BAD_REQUEST
            );
        }

        // Проверка баланса
        if (user.getBalance().compareTo(betRequest.getAmount()) < 0) {
            throw new CasinoRuntimeException(
                    "INSUFFICIENT_FUNDS",
                    "Insufficient balance for the bet",
                    HttpStatus.BAD_REQUEST
            );

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
                .orElseThrow(() -> new CasinoRuntimeException(
                        "NOT_FOUND",
                        "User not found",
                        HttpStatus.NOT_FOUND
                ));
        return betRepository.findByUser(user);
    }
}