package com.tictactoe.service;

import com.tictactoe.model.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * GameService - Business logic layer for game operations
 *
 * SOLID Principles:
 * - Single Responsibility: Coordinates game operations and lifecycle
 * - Dependency Inversion: Controller depends on this service abstraction
 *
 * Acts as facade between controller and domain model.
 * Manages current game instance and coordinates operations.
 *
 * @Service marks this as Spring-managed bean for dependency injection
 */
@Service
public class GameService {
    private Game currentGame;  // Active game instance

    /**
     * Constructor - Initializes service with new game
     * Called by Spring when creating the bean
     */
    public GameService() {
        initializeNewGame();
    }

    /**
     * Initializes new game with default settings (3x3, two human players)
     */
    public void initializeNewGame() {
        Player player1 = new HumanPlayer("X", "Player 1");
        Player player2 = new HumanPlayer("O", "Player 2");

        currentGame = new GameBuilder()
                .setBoardSize(3)
                .setPlayers(Arrays.asList(player1, player2))
                .build();
    }

    /**
     * Gets current active game
     * @return Current Game object
     */
    public Game getCurrentGame() {
        return currentGame;
    }

    /**
     * Makes a move at specified position
     * @param row Row index
     * @param col Column index
     * @return true if move successful
     */
    public boolean makeMove(int row, int col) {
        return currentGame.makeMove(row, col);
    }

    /**
     * Resets current game to initial state
     * Clears board and history, keeps same players
     */
    public void resetGame() {
        currentGame.reset();
    }

    /**
     * Undoes last move
     * @return true if undo successful, false if no moves to undo
     */
    public boolean undoMove() {
        return currentGame.undo();
    }

    /**
     * Starts new game with specified board size
     * @param boardSize Board dimension
     */
    public void startNewGame(int boardSize) {
        Player player1 = new HumanPlayer("X", "Player 1");
        Player player2 = new HumanPlayer("O", "Player 2");

        currentGame = new GameBuilder()
                .setBoardSize(boardSize)
                .setPlayers(Arrays.asList(player1, player2))
                .build();
    }
}