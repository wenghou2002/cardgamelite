package com.example.cardgame.model;

import java.util.Objects;

// Represents a playing card with a value (2-10, J, Q, K, A) and a suit (@, #, ^, *)
public class Card {
    private final String value;
    private final String suit;

    // Creates a new card with the given value and suit
    public Card(String value, String suit) {
        this.value = value;
        this.suit = suit;
    }

    // Gets the card's value (like "K" or "10")
    public String getValue() {
        return value;
    }

    // Gets the card's suit (like "@" or "*")
    public String getSuit() {
        return suit;
    }

    // Converts a string like "K@" or "10*" into a Card object
    public static Card fromString(String cardStr) {
        // Special case for "10" which is two characters
        if (cardStr.startsWith("10")) {
            return new Card("10", cardStr.substring(2));
        }
        
        // All other cards have a single character value
        return new Card(cardStr.substring(0, 1), cardStr.substring(1));
    }

    // Returns the card as a string (like "K@" or "10*")
    @Override
    public String toString() {
        return value + suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(value, card.value) && Objects.equals(suit, card.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, suit);
    }
} 