package com.tms.casino.service;

import com.tms.casino.exception.CasinoRuntimeException;
import com.tms.casino.model.User;
import com.tms.casino.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CasinoRuntimeException(
                        "NOT_FOUND",
                        "User not found with username: " + username,
                        HttpStatus.NOT_FOUND
                ));
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CasinoRuntimeException(
                        "NOT_FOUND",
                        "User not found with ID: " + userId,
                        HttpStatus.NOT_FOUND
                ));
    }

    @Transactional
    public User updateBalance(String username, BigDecimal amount) {
        User user = getUserByUsername(username);
        user.setBalance(user.getBalance().add(amount));
        return userRepository.save(user);
    }

    @Transactional
    public User verifyUser(Long userId) {
        User user = getUserById(userId);
        user.setVerified(true);
        return userRepository.save(user);
    }

    @Transactional
    public User blockUser(Long userId) {
        User user = getUserById(userId);
        user.setBlocked(true);
        return userRepository.save(user);
    }

    @Transactional
    public User unblockUser(Long userId) {
        User user = getUserById(userId);
        user.setBlocked(false);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId.longValue())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        userRepository.delete(user);
    }
}
