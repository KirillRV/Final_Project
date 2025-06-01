package com.tms.casino.service;

import com.tms.casino.exception.EntityNotFoundException;
import com.tms.casino.exception.GameNotActiveException;
import com.tms.casino.model.Game;
import com.tms.casino.repository.GameRepository;
import lombok.RequiredArgsConstructor;
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
                .orElseThrow(() -> new EntityNotFoundException("Game not found with ID: " + id));
    }

    public void checkGameActive(Long gameId) {
        Game game = getGameById(gameId);
        if (!game.isActive()) {
            throw new GameNotActiveException(game.getGameId());
        }
    }
}