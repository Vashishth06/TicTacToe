package com.tictactoe.strategy;

import com.tictactoe.model.Board;

/**
 * WinningStrategy - Strategy Pattern for determining game winners
 *
 * SOLID Principles:
 * - Open/Closed: New winning strategies can be added without modifying existing code
 * - Interface Segregation: Single focused method
 * - Dependency Inversion: Game depends on this abstraction, not concrete implementations
 *
 * Allows pluggable winning conditions (3-in-a-row, 4-in-a-row, custom patterns).
 * Game logic remains unchanged when adding new strategies.
 */
public interface WinningStrategy {

    /**
     * Checks if there is a winner on the board
     * @param board The game board to check
     * @return Winner's symbol ("X" or "O"), "TIE" if tied, or null if game continues
     */
    String checkWinner(Board board);
}