package com.tms.casino.controller;

import com.tms.casino.model.dto.BetRequest;
import com.tms.casino.model.Bet;
import com.tms.casino.model.dto.BetResult;
import com.tms.casino.service.BetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bets")
@RequiredArgsConstructor
public class BetController {

    private final BetService betService;

    @PostMapping
    public ResponseEntity<BetResult> placeBet(    // <-- BetResult вместо Bet
                                                  @AuthenticationPrincipal UserDetails userDetails,
                                                  @RequestBody BetRequest betRequest) {
        return ResponseEntity.ok(betService.placeBet(userDetails.getUsername(), betRequest));
    }

    @GetMapping("/history")
    public ResponseEntity<List<Bet>> getBetHistory(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(betService.getUserBetHistory(userDetails.getUsername()));
    }
}
