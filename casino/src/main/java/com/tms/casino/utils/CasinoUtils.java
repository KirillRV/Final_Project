package com.tms.casino.utils;

import com.tms.casino.model.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.Random;

@Component
public class CasinoUtils {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    // Генерация случайного промокода
    public String generatePromoCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = SECURE_RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    // Расчет бонуса для нового пользователя
    public BigDecimal calculateWelcomeBonus() {
        return BigDecimal.valueOf(50).setScale(2, RoundingMode.HALF_UP);
    }

    // Расчет кэшбэка на основе активности
    public BigDecimal calculateCashback(User user, BigDecimal betAmount) {
        BigDecimal cashbackPercent = BigDecimal.valueOf(0.05); // 5% по умолчанию
        if (user.getBalance().compareTo(BigDecimal.valueOf(1000)) > 0) {
            cashbackPercent = BigDecimal.valueOf(0.1); // VIP 10%
        }
        return betAmount.multiply(cashbackPercent).setScale(2, RoundingMode.HALF_UP);
    }

    // Проверка возраста пользователя
    public boolean isUserOfLegalAge(int age) {
        return age >= 18; // Минимальный возраст для азартных игр
    }

    // Генерация случайного результата для игры
    public int generateGameOutcome(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    // Форматирование суммы для отображения
    public String formatCurrency(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_UP) + " USD";
    }

    // Валидация платежных данных
    public boolean isValidPaymentMethod(String cardNumber) {
        // Простая валидация Luhn algorithm
        if (cardNumber == null || cardNumber.length() < 13) return false;

        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}