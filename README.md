# Card Game (Lite Version)

A full-stack web application for a multiplayer card game with Vue.js frontend and Spring Boot backend.

## Project Overview

This project is a web-based card game implementation that allows 4 players to play a game based on matching card values with specific winning rules.

### Game Rules

- Each player gets a hand of cards
- The winner is the player with the most cards of the same value (e.g., three Kings)
- In case of a tie, the player with the higher card value wins
- If still tied, the higher symbol value wins (♠ > ♥ > ♦ > ♣)

### Card Deck

Standard 52-card deck with the following card mapping:

| 2@    | 2#    | 2^    | 2*    | 3@    | 3#    | 3^    | 3*    | 4@    | 4#    | 4^    | 4*    |
|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|
| 5@    | 5#    | 5^    | 5*    | 6@    | 6#    | 6^    | 6*    | 7@    | 7#    | 7^    | 7*    |
| 8@    | 8#    | 8^    | 8*    | 9@    | 9#    | 9^    | 9*    | 10@   | 10#   | 10^   | 10*   |
| J@    | J#    | J^    | J*    | Q@    | Q#    | Q^    | Q*    | K@    | K#    | K^    | K*    |
| A@    | A#    | A^    | A*    |       |       |       |       |       |       |       |       |

Symbol mapping:
- @ = Club (♣) 
- \# = Diamond (♦) 
- ^ = Heart (♥)
- \* = Spade (♠)

Card values range from 2 (lowest) to A (Ace, highest).

## Tech Stack

### Frontend
- Vue 3 with Composition API
- TypeScript
- Tailwind CSS for styling
- Vue Router for navigation
- Axios for API communication
- Vite as build tool

### Backend
- Spring Boot 3.4.3
- Java 17
- RESTful API design

## Project Structure

```
card-game/
├── frontend/               # Vue.js frontend application
│   ├── src/
│   │   ├── assets/         # Static assets
│   │   ├── components/     # Reusable Vue components
│   │   ├── models/         # TypeScript interfaces
│   │   ├── router/         # Vue Router configuration
│   │   ├── services/       # API service layer
│   │   ├── views/          # Page components
│   │   └── utils/          # Utility functions
│   └── ...                 # Configuration files
│
├── backend/                # Spring Boot backend application
│   └── src/main/java/com/example/cardgame/
│       ├── controller/     # REST API controllers
│       ├── service/        # Business logic layer
│       ├── model/          # Data models
│       └── CardGameApplication.java  # Main application class
└── ...                     # Root configuration files
```

## Getting Started

### Prerequisites
- Node.js 16+ and npm
- Java 17
- Maven

### Installation

#### Install dependencies
```bash
# Install root project dependencies
npm install

# Install frontend dependencies
cd frontend
npm install
```

### Running the Application

#### Option 1: Run Frontend and Backend Concurrently (Recommended)
```bash
# From the project root
npm start
```

#### Option 2: Run Separately

**Frontend:**
```bash
cd frontend
npm run dev
```

**Backend:**
```bash
cd backend
mvn spring-boot:run
```

### Accessing the Application
- Frontend: http://localhost:5173
- Backend API: http://localhost:8080/api

## Features

- Interactive card game with visual representation
- Automatic card shuffling and dealing
- Game rules and instructions display
- Winner determination based on card matching rules
- Responsive design for different screen sizes

## API Endpoints

- `GET /api/shuffle-deal` - Shuffle, deal cards, and play a new game
- `POST /api/evaluate` - Determine the winner based on player hands

