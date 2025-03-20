package com.example.cardgame.model;

// This is the response for the evaluate endpoint
// Just tells you who won with their index
public class WinnerResponse {
    private final int winner;

    // Sets up a new response with the winner’s spot
    // Takes the index of the lucky player (starts at 0)
    public WinnerResponse(int winner) {
        this.winner = winner;
    }

    // Grabs the winner’s index
    // Hands you the number of the champ
    public int getWinner() {
        return winner;
    }
}