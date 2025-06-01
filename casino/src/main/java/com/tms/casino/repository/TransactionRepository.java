package com.tms.casino.repository;

import com.tms.casino.model.Transaction;
import com.tms.casino.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
    List<Transaction> findByUserAndType(User user, Transaction.TransactionType type);
    List<Transaction> findByStatus(Transaction.TransactionStatus status);
}