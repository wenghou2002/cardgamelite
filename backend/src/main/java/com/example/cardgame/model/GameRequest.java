package com.example.cardgame.model;

import java.util.List;

// Holds all the players' hands so we can figure out a winner
public class GameRequest {
    private List<List<String>> players;

    // Empty constructor so stuff like JSON can fill it in later
    public GameRequest() {
    }

    // Sets up a new game request with the players’ hands ready to go
    // Takes a list of each player’s cards
    public GameRequest(List<List<String>> players) {
        this.players = players;
    }

    // Grabs the list of players’ hands
    // Gives you back whatever’s in there
    public List<List<String>> getPlayers() {
        return players;
    }

    // Lets you set the players’ hands
    // Just pass in the list of card lists
    public void setPlayers(List<List<String>> players) {
        this.players = players;
    }
}