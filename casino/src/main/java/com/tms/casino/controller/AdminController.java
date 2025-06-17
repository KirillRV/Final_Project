package com.tms.casino.controller;

import com.tms.casino.model.User;
import com.tms.casino.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

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
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("Пользователь с id " + userId + " успешно удалён.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Пользователь с id " + userId + " не найден.");
        }
    }
}