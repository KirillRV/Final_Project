package com.tms.casino.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BetRequest {
    private Long gameId;
    private BigDecimal amount;
}
