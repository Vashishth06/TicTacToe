package com.tictactoe.model;

/**
 * HumanPlayer - Represents a human player
 *
 * Demonstrates Liskov Substitution Principle: Can be used anywhere
 * Player interface is expected without breaking functionality.
 *
 * Immutable class - all fields are final for thread safety and consistency.
 */
public class HumanPlayer implements Player {
    private final String symbol;  // Player's mark on board ("X" or "O")
    private final String name;    // Display name

    /**
     * Creates a new human player
     * @param symbol The symbol for this player (e.g., "X")
     * @param name The player's display name
     */
    public HumanPlayer(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PlayerType getType() {
        return PlayerType.HUMAN;
    }
}