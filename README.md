# 🎮 Tic Tac Toe - Professional Full-Stack Application

A modern, full-stack Tic-Tac-Toe game built with **Spring Boot** backend and **Vanilla JavaScript** frontend, demonstrating professional software engineering principles.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-green)
![License](https://img.shields.io/badge/License-MIT-blue)

---

## 📋 Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Design Principles](#design-principles)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [How to Play](#how-to-play)
- [Response Format](#response-format)
- [Future Enhancements](#future-enhancements)

---

## ✨ Features

- ✅ **Full-Stack Application**: Spring Boot REST API + Vanilla JavaScript frontend
- ✅ **Real-Time Gameplay**: Instant move updates and winner detection
- ✅ **Undo Functionality**: Revert your last move
- ✅ **Responsive Design**: Works on desktop, tablet, and mobile
- ✅ **Modern UI**: Beautiful gradient design with smooth animations
- ✅ **Accessibility**: ARIA labels, keyboard navigation support
- ✅ **Professional Architecture**: SOLID principles, design patterns, clean code
- ✅ **Winner Detection**: Automatic win/tie detection with celebration modal

---

## 🏗️ Architecture

This project follows **professional software engineering practices** with a clear separation of concerns:

### **Backend Architecture (Spring Boot)**

- **Model Layer**: Game logic, player management, board state
- **Service Layer**: Business logic coordination
- **Controller Layer**: REST API endpoints
- **Strategy Pattern**: Pluggable winning conditions

### **Frontend Architecture (JavaScript)**

- **Model**: `GameState` class - Data management
- **View**: `UIManager` class - DOM manipulation
- **Controller**: `GameController` class - Business logic
- **Service**: `APIService` class - API communication

---

## 🛠️ Tech Stack

### **Backend**
- **Java 17**
- **Spring Boot 3.1.5**
- **Maven** - Dependency management
- **REST API** - HTTP endpoints

### **Frontend**
- **HTML5** - Semantic markup
- **CSS3** - Modern styling with CSS Variables
- **Vanilla JavaScript (ES6+)** - No frameworks, pure JS
- **Fetch API** - Asynchronous communication

### **Tools**
- **IntelliJ IDEA** - IDE
- **Git** - Version control
- **Browser DevTools** - Debugging

---

## 📐 Design Principles

This project demonstrates industry-standard software engineering principles:

### **SOLID Principles**
- ✅ **Single Responsibility**: Each class has one clear purpose
- ✅ **Open/Closed**: Open for extension, closed for modification
- ✅ **Liskov Substitution**: Interfaces are interchangeable
- ✅ **Interface Segregation**: Small, focused interfaces
- ✅ **Dependency Inversion**: Depend on abstractions, not concrete classes

### **Design Patterns**
- ✅ **Strategy Pattern**: Pluggable winning strategies
- ✅ **Builder Pattern**: Flexible game construction
- ✅ **Dependency Injection**: Loose coupling via Spring
- ✅ **MVC Pattern**: Separation of concerns in frontend
- ✅ **Facade Pattern**: Simplified API interactions

### **OOP Concepts**
- ✅ **Encapsulation**: Private fields with public methods
- ✅ **Abstraction**: Interfaces define contracts
- ✅ **Inheritance**: Player implementations
- ✅ **Polymorphism**: Interchangeable components

### **Best Practices**
- ✅ **DRY**: Don't Repeat Yourself
- ✅ **KISS**: Keep It Simple, Stupid
- ✅ **Separation of Concerns**: Modular architecture
- ✅ **Clean Code**: Readable, maintainable, well-documented

---

## 📁 Project Structure
```
tictactoe-game/
├── src/
│   ├── main/
│   │   ├── java/com/tictactoe/
│   │   │   ├── TicTacToeApplication.java      # Main Spring Boot app
│   │   │   ├── controller/
│   │   │   │   └── GameController.java        # REST API endpoints
│   │   │   ├── service/
│   │   │   │   └── GameService.java           # Business logic
│   │   │   ├── model/
│   │   │   │   ├── Player.java                # Player interface
│   │   │   │   ├── HumanPlayer.java           # Human player
│   │   │   │   ├── BotPlayer.java             # Bot player
│   │   │   │   ├── Board.java                 # Board management
│   │   │   │   ├── Game.java                  # Game orchestration
│   │   │   │   ├── GameBuilder.java           # Builder pattern
│   │   │   │   ├── GameState.java             # Game state enum
│   │   │   │   ├── Move.java                  # Move value object
│   │   │   │   └── PlayerType.java            # Player type enum
│   │   │   └── strategy/
│   │   │       ├── WinningStrategy.java       # Strategy interface
│   │   │       └── DefaultWinningStrategy.java # Default implementation
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── index.html                 # Frontend HTML
│   │       │   ├── styles.css                 # Frontend CSS
│   │       │   └── app.js                     # Frontend JavaScript
│   │       └── application.properties         # Spring configuration
├── pom.xml                                     # Maven configuration
└── README.md                                   # This file
```

---



## 🔌 API Endpoints

Base URL: `http://localhost:8080/api/game`

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/status` | Get current game state | None | Game status JSON |
| POST | `/move` | Make a move | `{"row": 0, "col": 1}` | Updated game state |
| POST | `/reset` | Reset current game | None | Reset game state |
| POST | `/new` | Start new game | `{"size": 3}` (optional) | New game state |
| POST | `/undo` | Undo last move | None | Updated game state |

### **Example API Calls**

**Get Game Status**
```bash
curl http://localhost:8080/api/game/status
```
**Make a Move**
```
curl -X POST http://localhost:8080/api/game/move \
  -H "Content-Type: application/json" \
  -d '{"row": 0, "col": 0}'
```
**Reset Game**
```
curl -X POST http://localhost:8080/api/game/reset
```
**Start New Game**
```
curl -X POST http://localhost:8080/api/game/new \
  -H "Content-Type: application/json" \
  -d '{"size": 3}'
```
**Undo Last Move**
```
curl -X POST http://localhost:8080/api/game/undo
```

---

## 🎮 How to Play

### **Starting the Game**
1. Open your web browser
2. Navigate to `http://localhost:8080`
3. The game board will load automatically

### **Making Moves**
1. **Player X** always goes first
2. Click on any **empty cell** to place your mark
3. The game **automatically switches** to the next player (O)
4. Continue taking turns by clicking empty cells
5. The current player is displayed at the top of the board

### **Winning the Game**
- Get **3 marks in a row** to win:
  - **Horizontal**: Three across any row
  - **Vertical**: Three down any column
  - **Diagonal**: Three across either diagonal
- A celebration modal will appear announcing the winner! 🎉
- If all cells are filled with no winner, the game ends in a **tie**

### **Game Controls**

| Button | Description |
|--------|-------------|
| **Undo** | Takes back the last move |
| **Reset** | Clears the board and starts a fresh game with the same players |
| **New Game** | Starts a completely new game |

### **Keyboard Support**
- Press **Tab** to navigate between cells and buttons
- Press **Enter** or **Space** to select a cell or button
- Press **Escape** to close the winner modal

### **Tips**
- Plan your moves ahead to block your opponent
- Control the center cell for strategic advantage
- Watch for opponent's two-in-a-row patterns
- Use the Undo button to review different strategies


## 📄 Response Format

All endpoints return JSON with the following structure:
```
{
"board": [
["X", "O", ""],
["", "X", ""],
["", "", "O"]
],
"currentPlayer": "X",
"gameOver": false,
"winner": null,
"boardSize": 3
}
```

When a move is made, the response also includes:
```
{
  "success": true,
  "board": [...],
  "currentPlayer": "O",
  "gameOver": false,
  "winner": null,
  "boardSize": 3
}
```

## 🔮 Future Enhancements

### **Planned Features**
- [ ] **AI Bot Player** - Implement computer opponent using Minimax algorithm for optimal moves
- [ ] **Difficulty Levels** - Easy (random moves), Medium (basic strategy), Hard (unbeatable AI)
- [ ] **Player vs Player Online** - Multiplayer mode using WebSockets for real-time gameplay
- [ ] **Game History** - Save and display previous game results
- [ ] **Larger Boards** - Support for 4x4 and 5x5 board sizes
- [ ] **Dark Mode** - Toggle between light and dark themes
- [ ] **Sound Effects** - Add audio feedback for moves and wins


