import { Player } from './Player';

// Main game state - tracks players, loading state, errors, and winner
export interface GameState {
  players: Player[];       // Array of players in the game
  isLoading: boolean;      // Flag to indicate if game is loading
  error: string | null;    // Error message, if any
  winnerIndex: number | null; // Index of the winning player, if determined
}

// API response format for the shuffle and deal endpoint
export interface ShuffleDealResponse {
  players: string[][];    // Array of player hands (arrays of card strings)
}

// API response format for the winner evaluation endpoint
export interface EvaluateResponse {
  winner: number;         // Index of the winning player (0-based)
} 