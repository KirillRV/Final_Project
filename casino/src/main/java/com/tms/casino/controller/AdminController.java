package com.tms.casino.controller;

import com.tms.casino.model.User;
import com.tms.casino.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/block/{userId}")
    public ResponseEntity<User> blockUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.blockUser(userId));
    }

    @PutMapping("/unblock/{userId}")
    public ResponseEntity<User> unblockUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.unblockUser(userId));

    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId, @AuthenticationPrincipal UserDetails userDetails) {

        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        if (currentUser.getUserId().equals(userId)) {
            return ResponseEntity.badRequest().body("Administrator cannot delete themselves.");
        }

        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User with id " + userId + " has been successfully deleted.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("User with id " + userId + " was not found.");
        }
    }
}
