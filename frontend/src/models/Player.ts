import { Card } from './Card';

// Represents a player in the card game with their hand and status
export interface Player {
  id: number;          // Player ID (0-based index)
  name: string;        // Player name (e.g., "Player 1")
  hand: string[];      // Array of card strings (e.g., ["K@", "3#"])
  isWinner: boolean;   // Flag to indicate if this player is the winner
  matchingCount?: number;  // Number of matching cards (for winner display)
  matchingValue?: string;  // Value of the matching cards
}

// Counts the occurrences of each card value in a hand
export function countCardValues(hand: string[]): Record<string, number> {
  const counts: Record<string, number> = {};
  
  for (const cardStr of hand) {
    // Handle "10" special case
    const value = cardStr.startsWith('10') ? '10' : cardStr.charAt(0);
    
    if (!counts[value]) {
      counts[value] = 0;
    }
    counts[value]++;
  }
  
  return counts;
}

// Finds the value with the highest count in a hand
export function findHighestMatchingValue(hand: string[]): { value: string, count: number } {
  const counts = countCardValues(hand);
  let highestCount = 0;
  let highestValue = '';
  
  for (const [value, count] of Object.entries(counts)) {
    if (count > highestCount) {
      highestCount = count;
      highestValue = value;
    }
  }
  
  return { value: highestValue, count: highestCount };
}

// Gets all cards in a hand that match a specific value
export function getMatchingCards(hand: string[], value: string): string[] {
  return hand.filter(cardStr => {
    // Handle "10" special case
    const cardValue = cardStr.startsWith('10') ? '10' : cardStr.charAt(0);
    return cardValue === value;
  });
} 