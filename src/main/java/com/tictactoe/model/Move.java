package com.tictactoe.model;

/**
 * Move - Value Object representing a single move in the game
 *
 * Immutable class that encapsulates move information (position + player).
 * Used for move history and undo functionality.
 *
 * Follows Value Object pattern - all fields final, no setters.
 */
public class Move {
    private final int row;        // Row coordinate (0-based)
    private final int col;        // Column coordinate (0-based)
    private final Player player;  // Player who made this move

    /**
     * Creates an immutable Move object
     * @param row Row coordinate
     * @param col Column coordinate
     * @param player Player who made the move
     */
    public Move(int row, int col, Player player) {
        this.row = row;
        this.col = col;
        this.player = player;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Player getPlayer() {
        return player;
    }
}