package com.tms.casino.service;

import com.tms.casino.dto.BetRequest;
import com.tms.casino.model.Game;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Random;

@Service
public class GameEngineService {

    private final Random random = new Random();

    public BigDecimal processBet(Game game, BetRequest betRequest) {
        // Простая реализация игрового движка
        boolean isWin = random.nextDouble() < 0.4; // 40% шанс выигрыша

        if (isWin) {
            // Коэффициент выигрыша 1.5-3x
            double multiplier = 1.5 + random.nextDouble() * 1.5;
            return betRequest.getAmount().multiply(BigDecimal.valueOf(multiplier));
        }
        return BigDecimal.ZERO;
    }
}