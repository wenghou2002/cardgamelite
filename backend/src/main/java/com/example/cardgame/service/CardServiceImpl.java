package com.example.cardgame.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.cardgame.model.Card;
import com.example.cardgame.model.Player;

// Implementation of card game logic - handles shuffling, dealing, and finding winners
@Service
public class CardServiceImpl implements CardService {
    
    private static final Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);
    private static final int NUM_PLAYERS = 4;
    
    // Updated card values to match requirements
    private static final String[] CARD_VALUES = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    private static final String[] CARD_SUITS = {"@", "#", "^", "*"};
    
    // Creates a new card deck with the specified cards in the requirements
    @Override
    public List<String> initializeDeck() {
        List<String> deck = new ArrayList<>(52); // 13 values × 4 suits = 52 cards
        
        for (String value : CARD_VALUES) {
            for (String suit : CARD_SUITS) {
                deck.add(value + suit);
            }
        }
        
        logger.info("Initialized deck with {} cards", deck.size());
        return deck;
    }
    
    // Shuffles the deck using Fisher-Yates algorithm for efficiency
    @Override
    public void shuffleDeck(List<String> deck) {
        Random random = new Random();
        
        // Fisher-Yates shuffle algorithm (O(n) time, O(1) space)
        for (int i = deck.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            // Swap elements at i and j
            String temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);
        }
        
        logger.info("Shuffled deck of {} cards", deck.size());
    }
    
    // Deals cards to players in round-robin fashion
    @Override
    public List<Player> dealCards(List<String> deck, int numPlayers) {
        List<Player> players = new ArrayList<>(numPlayers);
        
        // Initialize players
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(i, "Player " + (i + 1)));
        }
        
        // Deal cards in round-robin fashion
        for (int i = 0; i < deck.size(); i++) {
            int playerIndex = i % numPlayers;
            players.get(playerIndex).addCard(deck.get(i));
        }
        
        logger.info("Dealt {} cards to {} players", deck.size(), numPlayers);
        return players;
    }
    
    // Finds the winner based on matching alphanumeric parts, with tiebreakers for value and symbol
    @Override
    public int determineWinner(List<List<String>> playersHands) {
        int winnerIndex = -1;
        int maxMatchingCards = 0;
        String maxValue = "";
        String maxSuit = "";
        
        // Process each player's hand
        for (int playerIndex = 0; playerIndex < playersHands.size(); playerIndex++) {
            List<String> hand = playersHands.get(playerIndex);
            
            // Count occurrences of each card value
            Map<String, Integer> valueCounts = new HashMap<>();
            for (String cardStr : hand) {
                Card card = Card.fromString(cardStr);
                String value = card.getValue();
                valueCounts.put(value, valueCounts.getOrDefault(value, 0) + 1);
            }
            
            // Find the maximum count for this player
            int playerMaxCount = 0;
            String playerMaxValue = "";
            
            for (Map.Entry<String, Integer> entry : valueCounts.entrySet()) {
                String value = entry.getKey();
                int count = entry.getValue();
                
                // If this count is better, or if it's equal but the value is higher
                if (count > playerMaxCount || 
                    (count == playerMaxCount && compareCardValues(value, playerMaxValue) > 0)) {
                    playerMaxCount = count;
                    playerMaxValue = value;
                }
            }
            
            // Determine if this player is the winner so far
            boolean newWinner = false;
            
            if (playerMaxCount > maxMatchingCards) {
                // Clear winner by count
                newWinner = true;
            } else if (playerMaxCount == maxMatchingCards && !playerMaxValue.isEmpty()) {
                // Tie on count, check value
                int valueComparison = compareCardValues(playerMaxValue, maxValue);
                if (valueComparison > 0) {
                    // Clear winner by value
                    newWinner = true;
                } else if (valueComparison == 0) {
                    // Tie on value, check suit (need to find highest suit for this value)
                    String playerMaxSuit = findHighestSuit(hand, playerMaxValue);
                    if (compareSuits(playerMaxSuit, maxSuit) > 0) {
                        newWinner = true;
                    }
                }
            }
            
            if (newWinner) {
                winnerIndex = playerIndex;
                maxMatchingCards = playerMaxCount;
                maxValue = playerMaxValue;
                maxSuit = findHighestSuit(hand, playerMaxValue);
                
                logger.info("New leader: Player {} with {} matching '{}' cards, highest suit: {}", 
                    playerIndex + 1, maxMatchingCards, maxValue, maxSuit);
            }
        }
        
        logger.info("Determined winner: Player {} (index {}) with {} matching '{}' cards, highest suit: {}", 
            winnerIndex + 1, winnerIndex, maxMatchingCards, maxValue, maxSuit);
        return winnerIndex;
    }
    
    // Combines initializing, shuffling and dealing for a complete game setup
    @Override
    public List<Player> playGame() {
        List<String> deck = initializeDeck();
        shuffleDeck(deck);
        return dealCards(deck, NUM_PLAYERS);
    }
    
    // Compares two card values to see which is higher (A > K > Q > etc.)
    private int compareCardValues(String value1, String value2) {
        int index1 = Arrays.asList(CARD_VALUES).indexOf(value1);
        int index2 = Arrays.asList(CARD_VALUES).indexOf(value2);
        return Integer.compare(index1, index2);
    }
    
    // Compares two card suits to see which is higher (* > ^ > # > @)
    private int compareSuits(String suit1, String suit2) {
        int index1 = Arrays.asList(CARD_SUITS).indexOf(suit1);
        int index2 = Arrays.asList(CARD_SUITS).indexOf(suit2);
        return Integer.compare(index1, index2);
    }
    
    // Finds the highest suit among cards with the same value in a hand
    private String findHighestSuit(List<String> hand, String value) {
        String highestSuit = "";
        
        for (String cardStr : hand) {
            Card card = Card.fromString(cardStr);
            if (card.getValue().equals(value)) {
                if (highestSuit.isEmpty() || compareSuits(card.getSuit(), highestSuit) > 0) {
                    highestSuit = card.getSuit();
                }
            }
        }
        
        return highestSuit;
    }
} 