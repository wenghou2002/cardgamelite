package com.example.cardgame.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cardgame.model.GameRequest;
import com.example.cardgame.model.GameResponse;
import com.example.cardgame.model.Player;
import com.example.cardgame.model.WinnerResponse;
import com.example.cardgame.service.CardService;

// Controller that handles all the card game API endpoints
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow requests from the Vue.js frontend
public class CardController {
    
    private static final Logger logger = LoggerFactory.getLogger(CardController.class);
    
    private final CardService cardService;
    
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }
    
    // Endpoint that shuffles the deck and deals cards to all players
    @GetMapping("/shuffle-deal")
    public ResponseEntity<GameResponse> shuffleAndDeal() {
        logger.info("Received request to shuffle and deal cards");
        
        try {
            // Play the game (initialize, shuffle, and deal)
            List<Player> players = cardService.playGame();
            
            // Extract player hands for the response
            List<List<String>> playerHands = players.stream()
                    .map(Player::getHand)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(new GameResponse(playerHands));
        } catch (Exception e) {
            logger.error("Error during shuffle and deal operation", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // Endpoint that determines which player is the winner based on the cards they have
    @PostMapping("/evaluate")
    public ResponseEntity<WinnerResponse> evaluateWinner(@RequestBody GameRequest request) {
        logger.info("Received request to evaluate winner");
        
        try {
            // Validate request
            if (request.getPlayers() == null || request.getPlayers().isEmpty()) {
                logger.warn("Invalid request: No players provided");
                return ResponseEntity.badRequest().build();
            }
            
            // Determine the winner
            int winnerIndex = cardService.determineWinner(request.getPlayers());
            
            return ResponseEntity.ok(new WinnerResponse(winnerIndex));
        } catch (Exception e) {
            logger.error("Error during winner evaluation", e);
            return ResponseEntity.internalServerError().build();
        }
    }
} 