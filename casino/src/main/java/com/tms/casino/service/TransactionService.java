package com.tms.casino.service;

import com.tms.casino.model.Transaction;
import com.tms.casino.model.Transaction.TransactionType;
import com.tms.casino.model.User;
import com.tms.casino.repository.TransactionRepository;
import com.tms.casino.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setCreatedAt(LocalDateTime.now());

        user.setBalance(user.getBalance().add(amount));
        userRepository.save(user);

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction createWithdrawal(String username, BigDecimal amount) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setCreatedAt(LocalDateTime.now());

        user.setBalance(user.getBalance().subtract(amount));
        userRepository.save(user);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getUserTransactions(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return transactionRepository.findByUser(user);
    }
}