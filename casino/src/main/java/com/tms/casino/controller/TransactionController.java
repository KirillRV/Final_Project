package com.tms.casino.controller;

import com.tms.casino.model.Transaction;
import com.tms.casino.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(transactionService.createDeposit(userDetails.getUsername(), amount));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(transactionService.createWithdrawal(userDetails.getUsername(), amount));
    }

    @GetMapping("/history")
    public ResponseEntity<List<Transaction>> getTransactionHistory(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(transactionService.getUserTransactions(userDetails.getUsername()));
    }
}