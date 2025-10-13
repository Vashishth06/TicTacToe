package com.tictactoe.model;

/**
 * Enum representing player types
 *
 * Used to distinguish player implementations without instanceof checks,
 * maintaining polymorphism and following good OOP practices.
 *
 * Can be extended for future player types (e.g., NETWORK, AI_HARD, AI_EASY)
 */
public enum PlayerType {
    HUMAN,  // Human player requiring manual input
    BOT     // Computer-controlled player
}