package com.tictactoe.controller;

import com.tictactoe.model.Game;
import com.tictactoe.model.GameState;
import com.tictactoe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * GameController - REST API for Tic-Tac-Toe game
 *
 * SOLID Principles:
 * - Single Responsibility: Only handles HTTP requests/responses
 * - Dependency Inversion: Depends on GameService abstraction (injected)
 *
 * Exposes REST endpoints for frontend interaction.
 * Translates HTTP requests to service calls and formats JSON responses.
 *
 * @RestController - Combines @Controller + @ResponseBody
 * @CrossOrigin - Allows cross-origin requests for frontend communication
 */
@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")
public class GameController {

    private final GameService gameService;  // Injected dependency

    /**
     * Constructor with dependency injection
     * @Autowired tells Spring to inject GameService bean
     */
    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * GET /api/game/status - Retrieves current game status
     * @return Map with board, current player, winner, etc.
     */
    @GetMapping("/status")
    public Map<String, Object> getGameStatus() {
        Game game = gameService.getCurrentGame();
        return buildGameStatusResponse(game);
    }

    /**
     * POST /api/game/move - Makes a move
     * Request body: {"row": 0, "col": 1}
     * @param moveRequest Map with row and col coordinates
     * @return Map with success status and updated game state
     */
    @PostMapping("/move")
    public Map<String, Object> makeMove(@RequestBody Map<String, Integer> moveRequest) {
        int row = moveRequest.get("row");
        int col = moveRequest.get("col");

        boolean success = gameService.makeMove(row, col);
        Game game = gameService.getCurrentGame();

        Map<String, Object> response = buildGameStatusResponse(game);
        response.put("success", success);

        return response;
    }

    /**
     * POST /api/game/reset - Resets current game
     * @return Map with reset game state
     */
    @PostMapping("/reset")
    public Map<String, Object> resetGame() {
        gameService.resetGame();
        return getGameStatus();
    }

    /**
     * POST /api/game/new - Starts new game
     * Request body (optional): {"size": 4}
     * @param config Optional map with board size
     * @return Map with new game state
     */
    @PostMapping("/new")
    public Map<String, Object> newGame(@RequestBody(required = false) Map<String, Integer> config) {
        int size = (config != null && config.containsKey("size")) ? config.get("size") : 3;
        gameService.startNewGame(size);
        return getGameStatus();
    }

    /**
     * POST /api/game/undo - Undoes last move
     * @return Map with success status and updated game state
     */
    @PostMapping("/undo")
    public Map<String, Object> undoMove() {
        boolean success = gameService.undoMove();
        Game game = gameService.getCurrentGame();

        Map<String, Object> response = buildGameStatusResponse(game);
        response.put("success", success);

        return response;
    }

    /**
     * Helper method to build standardized game status response
     * Converts Game object to Map for JSON serialization
     */
    private Map<String, Object> buildGameStatusResponse(Game game) {
        Map<String, Object> response = new HashMap<>();

        response.put("board", game.getBoard().getCells());
        response.put("currentPlayer", game.getCurrentPlayer().getSymbol());
        response.put("gameOver", game.getGameState() != GameState.IN_PROGRESS);

        // Determine winner string
        String winner = null;
        if (game.getGameState() == GameState.PLAYER_WON && game.getWinner() != null) {
            winner = game.getWinner().getSymbol();
        } else if (game.getGameState() == GameState.TIE) {
            winner = "TIE";
        }
        response.put("winner", winner);
        response.put("boardSize", game.getBoard().getSize());

        return response;
    }
}