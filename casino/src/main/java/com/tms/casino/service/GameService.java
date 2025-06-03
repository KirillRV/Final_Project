package com.tms.casino.service;

import com.tms.casino.exception.CasinoRuntimeException;
import com.tms.casino.model.Game;
import com.tms.casino.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public List<Game> getAllActiveGames() {
        return gameRepository.findByIsActive(true);
    }

    public Game getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new CasinoRuntimeException(
                        "NOT_FOUND",
                        "Game not found with ID: " + id,
                        HttpStatus.NOT_FOUND
                ));
    }

    public void checkGameActive(Long gameId) {
        Game game = getGameById(gameId);
        if (!game.isActive()) {
            throw new CasinoRuntimeException(
                    "GAME_NOT_ACTIVE",
                    "Game with id " + game.getGameId() + " is not active",
                    HttpStatus.BAD_REQUEST
            );
        }

    }
}