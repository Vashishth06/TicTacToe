package com.tictactoe.model;

/**
 * GameState - Represents current state of the game
 *
 * Using enum provides type safety, compile-time checking, and clear semantics
 * compared to booleans or strings.
 *
 * Can be extended with additional states (e.g., PAUSED, ABANDONED)
 */
public enum GameState {
    IN_PROGRESS,  // Game is ongoing
    PLAYER_WON,   // A player has won
    TIE           // Game ended in tie (board full, no winner)
}