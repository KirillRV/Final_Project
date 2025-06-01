package com.tms.casino.exception;

public class GameNotActiveException extends RuntimeException {
    public GameNotActiveException(Integer gameId) {
        super("Game with ID " + gameId + " is not active");
    }
}