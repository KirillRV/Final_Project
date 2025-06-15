package com.tms.casino.service;

import com.tms.casino.model.dto.BetRequest;
import com.tms.casino.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GameEngineService {

    private final Random random = new Random();

    public BigDecimal processBet(Game game, BigDecimal betAmount) {
        switch (game.getGameId().intValue()) {
            case 1: return playRoulette(betAmount);
            case 2: return playDice(betAmount);
            case 3: return playSlotMachine(betAmount);
            case 4: return playCardGame(betAmount);
            case 5: return playCoinFlip(betAmount);
            default: return playDefaultGame(betAmount);
        }
    }

    private BigDecimal playRoulette(BigDecimal amount) {
        // Roulette: 1/37 chance to win with 35x multiplier
        boolean win = random.nextInt(37) == 0;
        return win ? amount.multiply(BigDecimal.valueOf(35)) : BigDecimal.ZERO;
    }

    private BigDecimal playDice(BigDecimal amount) {
        // Dice: roll two dice, win if sum > 7, payout 2x
        int diceRoll = random.nextInt(6) + 1 + random.nextInt(6) + 1;
        boolean win = diceRoll > 7;
        return win ? amount.multiply(BigDecimal.valueOf(2)) : BigDecimal.ZERO;
    }

    private BigDecimal playSlotMachine(BigDecimal amount) {
        // Slot machine: 20% chance to win 5x
        boolean win = random.nextDouble() < 0.2;
        return win ? amount.multiply(BigDecimal.valueOf(5)) : BigDecimal.ZERO;
    }

    private BigDecimal playCardGame(BigDecimal amount) {
        // Card game: 50% chance to win 2x
        boolean win = random.nextBoolean();
        return win ? amount.multiply(BigDecimal.valueOf(2)) : BigDecimal.ZERO;
    }

    private BigDecimal playCoinFlip(BigDecimal amount) {
        // Coin flip: 50% chance to win 2x
        boolean win = random.nextBoolean();
        return win ? amount.multiply(BigDecimal.valueOf(2)) : BigDecimal.ZERO;
    }

    private BigDecimal playDefaultGame(BigDecimal amount) {
        // Default: 40% chance to win random multiplier between 1.5x and 3x
        boolean win = random.nextDouble() < 0.4;
        double multiplier = 1.5 + (random.nextDouble() * 1.5);
        return win ? amount.multiply(BigDecimal.valueOf(multiplier)) : BigDecimal.ZERO;
    }
}
