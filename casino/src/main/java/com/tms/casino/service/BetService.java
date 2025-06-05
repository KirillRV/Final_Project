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
        // Find user by username, throw exception if not found
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CasinoRuntimeException(
                        "NOT_FOUND",
                        "User not found",
                        HttpStatus.NOT_FOUND
                ));

        // Find game by ID from request, throw exception if not found
        Game game = gameRepository.findById(betRequest.getGameId())
                .orElseThrow(() -> new CasinoRuntimeException(
                        "NOT_FOUND",
                        "Game not found",
                        HttpStatus.NOT_FOUND
                ));

        if (!game.isActive()) {
            throw new CasinoRuntimeException(
                    "GAME_NOT_ACTIVE",
                    "Game with id " + game.getGameId() + " is not active",
                    HttpStatus.BAD_REQUEST
            );
        }

        // Check that the bet amount is not less than the game's minimum bet
        if (betRequest.getAmount().compareTo(game.getMinBet()) < 0) {
            throw new CasinoRuntimeException(
                    "INVALID_BET",
                    "Bet amount below minimum: " + game.getMinBet(),
                    HttpStatus.BAD_REQUEST
            );
        }

        // Check that the bet amount does not exceed the game's maximum bet
        if (betRequest.getAmount().compareTo(game.getMaxBet()) > 0) {
            throw new CasinoRuntimeException(
                    "INVALID_BET",
                    "Bet amount exceeds maximum: " + game.getMaxBet(),
                    HttpStatus.BAD_REQUEST
            );
        }

        if (betRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CasinoRuntimeException(
                    "INVALID_BET",
                    "Bet amount must be positive",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (user.getBalance().compareTo(betRequest.getAmount()) < 0) {
            throw new CasinoRuntimeException(
                    "INSUFFICIENT_FUNDS",
                    "Insufficient balance for the bet",
                    HttpStatus.BAD_REQUEST
            );
        }

        // Create new Bet and set its properties
        Bet bet = new Bet();
        bet.setUser(user);
        bet.setGame(game);
        bet.setAmount(betRequest.getAmount());
        bet.setCreatedAt(LocalDateTime.now());

        // Process the bet: call game engine service to calculate payout
        BigDecimal payout = gameEngineService.processBet(game, betRequest);
        bet.setPayout(payout);

        BigDecimal newBalance = user.getBalance()
                .subtract(betRequest.getAmount())
                .add(payout);
        user.setBalance(newBalance);

        // Save updated user
        userRepository.save(user);

        // Save and return the bet
        return betRepository.save(bet);
    }

    // Get bet history for user by username
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
