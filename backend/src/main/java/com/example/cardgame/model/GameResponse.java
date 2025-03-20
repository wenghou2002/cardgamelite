package com.example.cardgame.model;

import java.util.List;

// This is the response package for the shuffle-deal endpoint
// Holds all the players’ hands after we’ve shuffled and dealt
public class GameResponse {
    private final List<List<String>> players;

    // Sets up a new response with the players’ hands all sorted out
    // Takes the list of card lists for each player
    public GameResponse(List<List<String>> players) {
        this.players = players;
    }

    // Grabs the players’ hands for you
    // Just hands back the list of what everyone’s got
    public List<List<String>> getPlayers() {
        return players;
    }
}