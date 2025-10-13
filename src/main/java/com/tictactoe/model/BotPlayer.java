package com.tictactoe.model;

/**
 * BotPlayer - Represents a computer-controlled player
 *
 * Demonstrates Open/Closed Principle: Can be extended with subclasses
 * like SmartBotPlayer or EasyBotPlayer without modification.
 *
 * Future enhancements: Add AI strategies (Minimax, Random, etc.)
 */
public class BotPlayer implements Player {
    private final String symbol;  // Bot's mark on board ("X" or "O")
    private final String name;    // Display name (e.g., "Computer", "AI")

    /**
     * Creates a new bot player
     * @param symbol The symbol for this bot (e.g., "O")
     * @param name The bot's display name
     */
    public BotPlayer(String symbol, String name) {
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
        return PlayerType.BOT;
    }
}