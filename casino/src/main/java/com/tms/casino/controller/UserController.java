package com.tms.casino.controller;

import com.tms.casino.model.User;
import com.tms.casino.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getUserByUsername(userDetails.getUsername()));
    }

    @PutMapping("/balance")
    public ResponseEntity<User> updateBalance(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(userService.updateBalance(userDetails.getUsername(), amount));
    }

    @PutMapping("/verify")
    public ResponseEntity<User> verifyUser(@RequestParam Long userId) {
        return ResponseEntity.ok(userService.verifyUser(userId));
    }
}