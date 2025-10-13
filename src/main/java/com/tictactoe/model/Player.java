package com.tictactoe.model;

/**
 * Player Interface - Abstraction for all player types
 *
 * SOLID Principles:
 * - Interface Segregation: Small, focused interface
 * - Liskov Substitution: All implementations are interchangeable
 * - Dependency Inversion: Game depends on this, not concrete classes
 *
 * Enables polymorphism: Game works with any Player implementation
 * without knowing the specific type (HumanPlayer, BotPlayer, etc.)
 */
public interface Player {

    /**
     * Returns the player's board symbol (e.g., "X" or "O")
     * @return Player's symbol, never null or empty
     */
    String getSymbol();

    /**
     * Returns the player's display name
     * @return Player's name for UI display
     */
    String getName();

    /**
     * Returns the player type (HUMAN or BOT)
     * Avoids instanceof checks for better polymorphism
     * @return PlayerType enum value
     */
    PlayerType getType();
}