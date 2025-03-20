package com.example.cardgame.service;

import java.util.List;

import com.example.cardgame.model.Player;

public interface CardService {
    
    // Sets up a regular 52-card deck
    // Gives you a list of card strings, all nice and sorted
    List<String> initializeDeck();
    
    // Mixes up the deck real good
    // Takes the deck you give it and shuffles it
    void shuffleDeck(List<String> deck);
    
    // Hands out cards from the deck to however many players you want
    // Returns a list of players with their hands ready
    List<Player> dealCards(List<String> deck, int numPlayers);
    
    // Figures out who wins based on the game rules
    // Takes the players' hands and returns the winner’s spot in the list
    int determineWinner(List<List<String>> playersHands);
    
    // Runs the whole show: sets up, shuffles, deals, and picks a winner
    // Hands you back the list of players after dealing
    List<Player> playGame();
}