package com.tictactoe.strategy;

import com.tictactoe.model.Board;

/**
 * DefaultWinningStrategy - Standard Tic-Tac-Toe winning conditions
 *
 * Checks for 3-in-a-row: horizontally, vertically, or diagonally.
 * Also detects tie when board is full with no winner.
 *
 * Demonstrates Strategy Pattern - can be replaced with other strategies
 * (e.g., FourInARowStrategy) without modifying Game class.
 */
public class DefaultWinningStrategy implements WinningStrategy {

    /**
     * Checks all winning conditions on the board
     * @param board Game board to check
     * @return Winner's symbol, "TIE", or null if game continues
     */
    @Override
    public String checkWinner(Board board) {
        int size = board.getSize();

        // Check all rows
        for (int i = 0; i < size; i++) {
            if (checkLine(board.getCell(i, 0), board.getCell(i, 1), board.getCell(i, 2))) {
                return board.getCell(i, 0);
            }
        }

        // Check all columns
        for (int i = 0; i < size; i++) {
            if (checkLine(board.getCell(0, i), board.getCell(1, i), board.getCell(2, i))) {
                return board.getCell(0, i);
            }
        }

        // Check main diagonal (top-left to bottom-right)
        if (checkLine(board.getCell(0, 0), board.getCell(1, 1), board.getCell(2, 2))) {
            return board.getCell(0, 0);
        }

        // Check anti-diagonal (top-right to bottom-left)
        if (checkLine(board.getCell(0, 2), board.getCell(1, 1), board.getCell(2, 0))) {
            return board.getCell(0, 2);
        }

        // Check for tie
        if (board.isFull()) {
            return "TIE";
        }

        return null; // Game continues
    }

    /**
     * Checks if three cells form a winning line
     * @param a First cell
     * @param b Second cell
     * @param c Third cell
     * @return true if all three match and are non-empty
     */
    private boolean checkLine(String a, String b, String c) {
        return a != null && !a.isEmpty() && a.equals(b) && b.equals(c);
    }
}