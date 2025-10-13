package com.tictactoe.model;

import com.tictactoe.strategy.WinningStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Game - Orchestrates game flow and manages game state
 *
 * SOLID Principles:
 * - Single Responsibility: Manages game state and turn logic only
 * - Open/Closed: Extensible via WinningStrategy without modification
 * - Dependency Inversion: Depends on WinningStrategy abstraction
 *
 * Coordinates Board, Players, and WinningStrategy to run the game.
 */
public class Game {
    private final Board board;                    // Game board
    private final List<Player> players;           // Players in game
    private final WinningStrategy winningStrategy; // Winner determination strategy
    private final List<Move> moveHistory;         // Move history for undo

    private int currentPlayerIndex;               // Current player's turn
    private GameState gameState;                  // Current game state
    private Player winner;                        // Winner (null if no winner)

    /**
     * Creates a new game with dependency injection
     * @param board Game board
     * @param players List of players
     * @param winningStrategy Strategy to determine winner
     */
    public Game(Board board, List<Player> players, WinningStrategy winningStrategy) {
        this.board = board;
        this.players = players;
        this.winningStrategy = winningStrategy;
        this.moveHistory = new ArrayList<>();
        this.currentPlayerIndex = 0;
        this.gameState = GameState.IN_PROGRESS;
        this.winner = null;
    }

    /**
     * Attempts to make a move at specified position
     * @param row Row index
     * @param col Column index
     * @return true if move successful, false otherwise
     */
    public boolean makeMove(int row, int col) {
        // Validate game is ongoing
        if (gameState != GameState.IN_PROGRESS) {
            return false;
        }

        // Validate cell is empty
        if (!board.isCellEmpty(row, col)) {
            return false;
        }

        // Place symbol and record move
        Player currentPlayer = getCurrentPlayer();
        board.setCell(row, col, currentPlayer.getSymbol());
        moveHistory.add(new Move(row, col, currentPlayer));

        // Check for winner or tie
        checkGameState();

        // Switch to next player if game continues
        if (gameState == GameState.IN_PROGRESS) {
            switchPlayer();
        }

        return true;
    }

    /**
     * Checks game state using winning strategy
     * Updates gameState and winner if game ended
     */
    private void checkGameState() {
        String result = winningStrategy.checkWinner(board);

        if (result != null) {
            if (result.equals("TIE")) {
                gameState = GameState.TIE;
            } else {
                gameState = GameState.PLAYER_WON;
                winner = findPlayerBySymbol(result);
            }
        }
    }

    /**
     * Finds player by their symbol
     * @param symbol Player symbol to search
     * @return Player with matching symbol, or null
     */
    private Player findPlayerBySymbol(String symbol) {
        return players.stream()
                .filter(p -> p.getSymbol().equals(symbol))
                .findFirst()
                .orElse(null);
    }

    /**
     * Switches to next player's turn
     * Uses modulo for cycling through players
     */
    private void switchPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    /**
     * Gets current player
     * @return Current Player object
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Undoes the last move
     * @return true if undo successful, false if no moves to undo
     */
    public boolean undo() {
        if (moveHistory.isEmpty()) {
            return false;
        }

        // Remove last move and clear cell
        Move lastMove = moveHistory.remove(moveHistory.size() - 1);
        board.setCell(lastMove.getRow(), lastMove.getCol(), "");

        // Reset game state
        gameState = GameState.IN_PROGRESS;
        winner = null;

        // Switch back to previous player
        currentPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();

        return true;
    }

    /**
     * Resets game to initial state
     * Clears board and history, keeps same players
     */
    public void reset() {
        board.reset();
        moveHistory.clear();
        currentPlayerIndex = 0;
        gameState = GameState.IN_PROGRESS;
        winner = null;
    }

    // Getters
    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Player getWinner() {
        return winner;
    }

    /**
     * Returns copy of move history to prevent external modification
     * @return List of moves
     */
    public List<Move> getMoveHistory() {
        return new ArrayList<>(moveHistory);
    }
}