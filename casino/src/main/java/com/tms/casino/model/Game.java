package com.tms.casino.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "games")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gameId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private boolean isActive;

    @Column(precision = 10, scale = 2)
    private BigDecimal minBet;

    @Column(precision = 10, scale = 2)
    private BigDecimal maxBet;

    @Column(precision = 5, scale = 2)
    private BigDecimal rtp; // (Return to Player)

    private String volatility; // Low, medium, high
}