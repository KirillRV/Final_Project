package com.tms.casino.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BetResult {
    private boolean win;           // Whether the bet was a win or not
    private BigDecimal amount;     // The amount won (0 if lost)
    private BigDecimal betAmount;  // The amount that was bet (for convenience)
    private String message;        // Message about the result (e.g., "You won!", "You lost, try again")
}
