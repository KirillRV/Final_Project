package com.tms.casino.service;

import com.tms.casino.exception.CasinoRuntimeException;
import com.tms.casino.model.Transaction;
import com.tms.casino.model.Transaction.TransactionType;
import com.tms.casino.model.User;
import com.tms.casino.repository.TransactionRepository;
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
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Transactional
    public Transaction createDeposit(String username, BigDecimal amount) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CasinoRuntimeException(
                        "NOT_FOUND",
                        "User not found",
                        HttpStatus.NOT_FOUND
                ));

        Transaction transaction = Transaction.builder()
                .user(user)
                .amount(amount)
                .type(TransactionType.DEPOSIT)
                .status(Transaction.TransactionStatus.COMPLETED)
                .createdAt(LocalDateTime.now())
                .build();

        user.setBalance(user.getBalance().add(amount));
        userRepository.save(user);

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction createWithdrawal(String username, BigDecimal amount) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CasinoRuntimeException(
                        "NOT_FOUND",
                        "User not found",
                        HttpStatus.NOT_FOUND
                ));

        if (user.getBalance().compareTo(amount) < 0) {
            throw new CasinoRuntimeException(
                    "INSUFFICIENT_FUNDS",
                    "Available balance: " + user.getBalance(),
                    HttpStatus.BAD_REQUEST
            );
        }


        Transaction transaction = Transaction.builder()
                .user(user)
                .amount(amount)
                .type(TransactionType.WITHDRAWAL)
                .status(Transaction.TransactionStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        user.setBalance(user.getBalance().subtract(amount));
        userRepository.save(user);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getUserTransactions(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CasinoRuntimeException(
                        "NOT_FOUND",
                        "User not found",
                        HttpStatus.NOT_FOUND
                ));
        return transactionRepository.findByUser(user);
    }
}