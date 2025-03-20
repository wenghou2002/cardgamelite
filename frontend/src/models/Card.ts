// A simple card with a value and suit, like "K@" or "A*"
export interface Card {
  value: string; 
  suit: string;  // The symbol part (@, #, ^, *)
}

// All card values from low to high
export const CARD_VALUES = ['2', '3', '4', '5', '6', '7', '8', '9', '10', 'J', 'Q', 'K', 'A'];

// All card suits from low to high
export const CARD_SUITS = ['@', '#', '^', '*'];

// Turns a card string like "K@" into a Card object with value and suit split up
export function parseCard(cardStr: string): Card {
  // Gotta handle "10" separately since it’s two characters
  if (cardStr.startsWith('10')) {
    return {
      value: '10',
      suit: cardStr.substring(2)
    };
  }
  
  // For everything else, it’s just one character for the value
  return {
    value: cardStr.substring(0, 1),
    suit: cardStr.substring(1)
  };
}

// Takes a Card object and smashes it back into a string like "K@" or "10*"
export function formatCard(card: Card): string {
  return `${card.value}${card.suit}`;
}