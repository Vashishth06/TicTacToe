package com.tictactoe.model;

/**
 * Board - Manages the game board state
 *
 * Single Responsibility: Only handles board operations (cell management,
 * validation, state queries). Winner checking is delegated to WinningStrategy.
 *
 * Encapsulates board implementation details from game logic.
 */
public class Board {
    private final int size;        // Board dimension (e.g., 3 for 3x3)
    private final String[][] cells; // 2D array storing cell values

    /**
     * Creates a new board with specified size
     * @param size Board dimension (minimum 3)
     */
    public Board(int size) {
        this.size = size;
        this.cells = new String[size][size];
        initialize();
    }

    /**
     * Initializes all cells to empty strings
     */
    private void initialize() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = "";
            }
        }
    }

    /**
     * Checks if a cell is empty
     * @param row Row index (0-based)
     * @param col Column index (0-based)
     * @return true if cell is empty and position is valid
     */
    public boolean isCellEmpty(int row, int col) {
        return isValidPosition(row, col) && cells[row][col].isEmpty();
    }

    /**
     * Validates if position is within board boundaries
     * @param row Row index
     * @param col Column index
     * @return true if position is valid
     */
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    /**
     * Sets a cell to specified symbol
     * @param row Row index
     * @param col Column index
     * @param symbol Symbol to place ("X" or "O")
     */
    public void setCell(int row, int col, String symbol) {
        if (isValidPosition(row, col)) {
            cells[row][col] = symbol;
        }
    }

    /**
     * Gets the value of a cell
     * @param row Row index
     * @param col Column index
     * @return Cell value, or null if invalid position
     */
    public String getCell(int row, int col) {
        return isValidPosition(row, col) ? cells[row][col] : null;
    }

    /**
     * Checks if all cells are filled
     * @return true if board is full
     */
    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cells[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Resets board to empty state
     */
    public void reset() {
        initialize();
    }

    /**
     * Returns the cell array (not a copy)
     * @return 2D array of cells
     */
    public String[][] getCells() {
        return cells;
    }

    /**
     * Returns board size
     * @return Board dimension
     */
    public int getSize() {
        return size;
    }
}