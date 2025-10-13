/**
 * Tic Tac Toe Game - Frontend Application
 *
 * Architecture: MVC Pattern
 * - APIService: Handles all backend communication
 * - GameState: Manages game data
 * - UIManager: Handles all DOM manipulation
 * - GameController: Coordinates business logic
 * - App: Initializes and connects everything
 */

// ============================================================================
// CONFIG - Configuration constants (Single source of truth)
// ============================================================================
const CONFIG = {
    API_URL: 'http://localhost:8080/api/game',
    BOARD_SIZE: 3,
    ANIMATION_DELAY: 300,
    MESSAGES: {
        LOADING: 'Loading game...',
        READY: 'Ready to play!',
        SUCCESS: 'success',
        ERROR: 'error',
        INVALID_MOVE: 'Invalid move! Cell already taken.',
        GAME_RESET: 'Game reset!',
        NEW_GAME: 'New game started!',
        MOVE_UNDONE: 'Move undone!',
        NO_UNDO: 'Cannot undo - no moves to undo',
        CONNECTION_ERROR: 'Error: Make sure backend is running on port 8080'
    }
};

// ============================================================================
// API SERVICE - Single Responsibility: Handle all API communication
// ============================================================================
class APIService {
    constructor(baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Generic fetch wrapper with error handling
     * DRY principle - avoid repeating fetch logic
     */
    async request(endpoint, options = {}) {
        try {
            const response = await fetch(`${this.baseUrl}${endpoint}`, {
                headers: { 'Content-Type': 'application/json' },
                ...options
            });
            return await response.json();
        } catch (error) {
            console.error(`API Error [${endpoint}]:`, error);
            throw error;
        }
    }

    /**
     * Gets current game status
     */
    async getStatus() {
        return this.request('/status');
    }

    /**
     * Makes a move at specified position
     */
    async makeMove(row, col) {
        return this.request('/move', {
            method: 'POST',
            body: JSON.stringify({ row, col })
        });
    }

    /**
     * Resets current game
     */
    async resetGame() {
        return this.request('/reset', { method: 'POST' });
    }

    /**
     * Starts new game with optional size
     */
    async newGame(size = 3) {
        return this.request('/new', {
            method: 'POST',
            body: JSON.stringify({ size })
        });
    }

    /**
     * Undoes last move
     */
    async undoMove() {
        return this.request('/undo', { method: 'POST' });
    }
}

// ============================================================================
// GAME STATE - Single Responsibility: Manage game data
// ============================================================================
class GameState {
    constructor(data) {
        this.board = data.board;
        this.currentPlayer = data.currentPlayer;
        this.gameOver = data.gameOver;
        this.winner = data.winner;
        this.boardSize = data.boardSize;
    }

    /**
     * Query methods for game state (Encapsulation)
     */
    isGameOver() {
        return this.gameOver;
    }

    getWinner() {
        return this.winner;
    }

    getCurrentPlayer() {
        return this.currentPlayer;
    }

    getBoard() {
        return this.board;
    }

    getBoardSize() {
        return this.boardSize;
    }
}

// ============================================================================
// UI MANAGER - Single Responsibility: Handle all DOM manipulation
// ============================================================================
class UIManager {
    constructor() {
        // Cache DOM elements for performance (avoid repeated queries)
        this.elements = this.cacheElements();
    }

    /**
     * Caches all required DOM elements
     */
    cacheElements() {
        return {
            board: document.getElementById('board'),
            currentPlayer: document.getElementById('currentPlayer'),
            status: document.getElementById('status'),
            winnerModal: document.getElementById('winnerModal'),
            winnerText: document.getElementById('winnerText'),
            winnerMessage: document.getElementById('winnerMessage'),
            undoBtn: document.getElementById('undoBtn'),
            resetBtn: document.getElementById('resetBtn'),
            newGameBtn: document.getElementById('newGameBtn'),
            playAgainBtn: document.getElementById('playAgainBtn')
        };
    }

    /**
     * Renders the complete game board
     * Separation of Concerns - only handles UI rendering
     */
    renderBoard(gameState, onCellClick) {
        this.clearBoard();
        const board = gameState.getBoard();

        board.forEach((row, rowIndex) => {
            row.forEach((cellValue, colIndex) => {
                const cellElement = this.createCellElement(
                    cellValue,
                    rowIndex,
                    colIndex,
                    gameState,
                    onCellClick
                );
                this.elements.board.appendChild(cellElement);
            });
        });
    }

    /**
     * Creates a single cell element
     * Component-based approach - reusable cell creation
     */
    createCellElement(cellValue, row, col, gameState, onCellClick) {
        const cell = document.createElement('button');
        cell.className = 'cell';
        cell.textContent = cellValue;
        cell.setAttribute('role', 'gridcell');
        cell.setAttribute('aria-label', `Cell ${row + 1}, ${col + 1}`);

        // Apply appropriate classes based on cell state
        if (cellValue) {
            cell.classList.add('taken', cellValue.toLowerCase());
            cell.setAttribute('aria-label', `${cellValue} in cell ${row + 1}, ${col + 1}`);
        }

        // Add click handler only for valid moves
        if (!gameState.isGameOver() && !cellValue) {
            cell.onclick = () => onCellClick(row, col);
            cell.setAttribute('tabindex', '0');
        } else {
            cell.classList.add('taken');
            cell.setAttribute('tabindex', '-1');
        }

        return cell;
    }

    /**
     * Clears the board (helper method)
     */
    clearBoard() {
        this.elements.board.innerHTML = '';
    }

    /**
     * Updates current player display
     */
    updatePlayerInfo(gameState) {
        const player = gameState.getCurrentPlayer();
        const playerClass = player === 'X' ? 'player-x' : 'player-o';

        this.elements.currentPlayer.innerHTML = gameState.isGameOver()
            ? 'Game Over'
            : `Current Player: <span class="${playerClass}">${player}</span>`;
    }

    /**
     * Shows winner modal with animation
     */
    showWinnerModal(winner) {
        const { winnerText, winnerMessage, winnerModal } = this.elements;

        if (winner === 'TIE') {
            winnerText.textContent = '🤝';
            winnerMessage.textContent = "It's a Tie!";
        } else {
            winnerText.textContent = '🎉';
            winnerMessage.textContent = `Player ${winner} Wins!`;
        }

        winnerModal.classList.add('show');
        winnerModal.setAttribute('aria-hidden', 'false');
    }

    /**
     * Hides winner modal
     */
    hideWinnerModal() {
        this.elements.winnerModal.classList.remove('show');
        this.elements.winnerModal.setAttribute('aria-hidden', 'true');
    }

    /**
     * Shows status message with appropriate styling
     */
    showStatus(message, type = '') {
        this.elements.status.textContent = message;
        this.elements.status.className = `status ${type}`;
    }

    /**
     * Gets cached element reference
     */
    getElement(name) {
        return this.elements[name];
    }
}

// ============================================================================
// GAME CONTROLLER - Single Responsibility: Coordinate game logic
// ============================================================================
class GameController {
    constructor(apiService, uiManager) {
        this.api = apiService;
        this.ui = uiManager;
        this.gameState = null;
    }

    /**
     * Initializes the game
     * Loads initial state from backend
     */
    async init() {
        try {
            this.ui.showStatus(CONFIG.MESSAGES.LOADING);
            const data = await this.api.getStatus();
            this.updateGameState(data);
            this.ui.showStatus(CONFIG.MESSAGES.READY, CONFIG.MESSAGES.SUCCESS);
        } catch (error) {
            this.handleError(CONFIG.MESSAGES.CONNECTION_ERROR);
        }
    }

    /**
     * Updates game state and refreshes UI
     * Central update method - maintains consistency
     */
    updateGameState(data) {
        this.gameState = new GameState(data);
        this.ui.renderBoard(this.gameState, (row, col) => this.makeMove(row, col));
        this.ui.updatePlayerInfo(this.gameState);
    }

    /**
     * Makes a move at specified position
     * Handles move logic and win detection
     */
    async makeMove(row, col) {
        // Guard clause - prevent moves if game is over
        if (this.gameState.isGameOver()) return;

        try {
            const data = await this.api.makeMove(row, col);

            if (data.success) {
                this.updateGameState(data);

                // Show winner modal after animation delay if game ended
                if (data.gameOver) {
                    setTimeout(() => {
                        this.ui.showWinnerModal(data.winner);
                    }, CONFIG.ANIMATION_DELAY);
                }
            } else {
                this.ui.showStatus(CONFIG.MESSAGES.INVALID_MOVE, CONFIG.MESSAGES.ERROR);
            }
        } catch (error) {
            this.handleError('Error making move');
        }
    }

    /**
     * Resets the current game
     */
    async reset() {
        try {
            const data = await this.api.resetGame();
            this.updateGameState(data);
            this.ui.hideWinnerModal();
            this.ui.showStatus(CONFIG.MESSAGES.GAME_RESET, CONFIG.MESSAGES.SUCCESS);
        } catch (error) {
            this.handleError('Error resetting game');
        }
    }

    /**
     * Starts a completely new game
     */
    async newGame() {
        try {
            const data = await this.api.newGame(CONFIG.BOARD_SIZE);
            this.updateGameState(data);
            this.ui.hideWinnerModal();
            this.ui.showStatus(CONFIG.MESSAGES.NEW_GAME, CONFIG.MESSAGES.SUCCESS);
        } catch (error) {
            this.handleError('Error starting new game');
        }
    }

    /**
     * Undoes the last move
     */
    async undo() {
        try {
            const data = await this.api.undoMove();

            if (data.success) {
                this.updateGameState(data);
                this.ui.showStatus(CONFIG.MESSAGES.MOVE_UNDONE, CONFIG.MESSAGES.SUCCESS);
            } else {
                this.ui.showStatus(CONFIG.MESSAGES.NO_UNDO, CONFIG.MESSAGES.ERROR);
            }
        } catch (error) {
            this.handleError('Error undoing move');
        }
    }

    /**
     * Centralized error handling
     */
    handleError(message) {
        this.ui.showStatus(message, CONFIG.MESSAGES.ERROR);
        console.error(message);
    }
}

// ============================================================================
// APPLICATION - Main application class (Facade pattern)
// ============================================================================
class App {
    constructor() {
        // Dependency Injection - inject dependencies into components
        this.api = new APIService(CONFIG.API_URL);
        this.ui = new UIManager();
        this.controller = new GameController(this.api, this.ui);
    }

    /**
     * Initializes the application
     * Sets up event listeners and loads initial game state
     */
    async init() {
        // Initialize game state
        await this.controller.init();

        // Set up event listeners
        this.setupEventListeners();

        console.log('✅ Tic Tac Toe application initialized');
    }

    /**
     * Sets up all event listeners
     * Separation of Concerns - centralized event management
     */
    setupEventListeners() {
        // Undo button
        this.ui.getElement('undoBtn').addEventListener('click', () => {
            this.controller.undo();
        });

        // Reset button
        this.ui.getElement('resetBtn').addEventListener('click', () => {
            this.controller.reset();
        });

        // New Game button
        this.ui.getElement('newGameBtn').addEventListener('click', () => {
            this.controller.newGame();
        });

        // Play Again button (in modal)
        this.ui.getElement('playAgainBtn').addEventListener('click', () => {
            this.controller.reset();
        });

        // Close modal on overlay click
        this.ui.getElement('winnerModal').addEventListener('click', (e) => {
            if (e.target.id === 'winnerModal') {
                this.controller.reset();
            }
        });

        // Keyboard support for accessibility
        this.setupKeyboardNavigation();
    }

    /**
     * Sets up keyboard navigation for accessibility
     */
    setupKeyboardNavigation() {
        document.addEventListener('keydown', (e) => {
            // ESC key closes modal
            if (e.key === 'Escape') {
                this.ui.hideWinnerModal();
            }
        });
    }
}

// ============================================================================
// APPLICATION INITIALIZATION - Entry point
// ============================================================================

/**
 * Initialize application when DOM is fully loaded
 * Ensures all elements are available before accessing them
 */
document.addEventListener('DOMContentLoaded', () => {
    const app = new App();
    app.init();
});

/**
 * Optional: Add service worker for offline support (PWA)
 * Uncomment to enable offline functionality
 */
/*
if ('serviceWorker' in navigator) {
    window.addEventListener('load', () => {
        navigator.serviceWorker.register('/service-worker.js')
            .then(reg => console.log('✅ Service Worker registered'))
            .catch(err => console.log('❌ Service Worker registration failed'));
    });
}
*/