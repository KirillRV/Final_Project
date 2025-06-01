package com.tms.casino.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bonuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bonus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bonusId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private BonusType type;

    @Column(precision = 15, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private BonusStatus status;

    private BigDecimal wageringRequirement; // Требование по отыгрышу
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;

    public enum BonusType {
        WELCOME, DEPOSIT, FREE_SPINS, CASHBACK
    }

    public enum BonusStatus {
        ACTIVE, USED, EXPIRED
    }
}