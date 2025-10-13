package com.tictactoe.model;

import com.tictactoe.strategy.DefaultWinningStrategy;
import com.tictactoe.strategy.WinningStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * GameBuilder - Builder Pattern for flexible Game construction
 *
 * Provides fluent API for creating Game objects with validation.
 * Allows step-by-step construction with default values.
 *
 * Example:
 * Game game = new GameBuilder()
 *     .setBoardSize(3)
 *     .addPlayer(new HumanPlayer("X", "Alice"))
 *     .addPlayer(new BotPlayer("O", "Computer"))
 *     .build();
 */
public class GameBuilder {
    private int boardSize = 3;  // Default 3x3 board
    private List<Player> players = new ArrayList<>();
    private WinningStrategy winningStrategy = new DefaultWinningStrategy();

    /**
     * Sets board size
     * @param size Board dimension (e.g., 3 for 3x3)
     * @return this builder for method chaining
     */
    public GameBuilder setBoardSize(int size) {
        this.boardSize = size;
        return this;
    }

    /**
     * Adds a player to the game
     * @param player Player to add
     * @return this builder for method chaining
     */
    public GameBuilder addPlayer(Player player) {
        this.players.add(player);
        return this;
    }

    /**
     * Sets all players (replaces existing)
     * @param players List of players
     * @return this builder for method chaining
     */
    public GameBuilder setPlayers(List<Player> players) {
        this.players = players;
        return this;
    }

    /**
     * Sets winning strategy (Strategy Pattern)
     * @param strategy WinningStrategy implementation
     * @return this builder for method chaining
     */
    public GameBuilder setWinningStrategy(WinningStrategy strategy) {
        this.winningStrategy = strategy;
        return this;
    }

    /**
     * Builds and returns configured Game object
     * @return Constructed Game instance
     * @throws IllegalStateException if configuration invalid
     */
    public Game build() {
        validateGameConfiguration();
        Board board = new Board(boardSize);
        return new Game(board, players, winningStrategy);
    }

    /**
     * Validates game configuration before building
     * @throws IllegalStateException if invalid configuration
     */
    private void validateGameConfiguration() {
        if (players.size() < 2) {
            throw new IllegalStateException("Game must have at least 2 players");
        }

        if (boardSize < 3) {
            throw new IllegalStateException("Board size must be at least 3");
        }
    }
}