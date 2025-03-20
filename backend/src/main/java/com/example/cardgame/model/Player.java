package com.example.cardgame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// This is a player in our card game
// They’ve got an ID, a name, and some cards in their hand
public class Player {
    private final int id;
    private final String name;
    private final List<String> hand;
    private boolean isWinner;

    // Sets up a new player with an ID and a name
    // ID’s just a number starting at 0, name’s whatever you call them
    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.hand = new ArrayList<>(); // Fresh empty hand to start
        this.isWinner = false; // Nobody’s a winner ‘til we say so
    }

    // Grabs the player’s ID
    // Just hands you their number
    public int getId() {
        return id;
    }

    // Gets the player’s name
    // Tells you what they’re called
    public String getName() {
        return name;
    }

    // Shows you the player’s hand
    // Returns the list of cards they’re holding
    public List<String> getHand() {
        return hand;
    }

    // Tosses a card into the player’s hand
    // Just give it a card and it’s theirs
    public void addCard(String card) {
        hand.add(card);
    }

    // Checks if this player’s the champ
    // Returns true if they’ve won, false if not
    public boolean isWinner() {
        return isWinner;
    }

    // Marks this player as the winner (or not)
    // Set it to true if they’ve got the goods
    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    // Checks if two players are the same based on ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Same object, duh
        if (o == null || getClass() != o.getClass()) return false; // Not even a player? Nope
        Player player = (Player) o;
        return id == player.id; // Same ID means same player
    }

    // Gives a hash code based on the ID
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Turns the player into a nice string for printing
    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hand=" + hand +
                ", isWinner=" + isWinner +
                '}';
    }
}