// Maps the suit symbols to their corresponding Lucide icon names
export const SUIT_TO_ICON = {
  '@': 'club',
  '#': 'diamond',
  '^': 'heart',
  '*': 'spade'
};

// Maps the suit symbols to their names
export const SUIT_TO_NAME = {
  '@': 'Clubs',
  '#': 'Diamonds',
  '^': 'Hearts',
  '*': 'Spades'
};

// Maps the suit symbols to their colors
export const SUIT_TO_COLOR = {
  '@': 'black',
  '#': 'red',
  '^': 'red',
  '*': 'black'
};

// Gets the corresponding Lucide icon name for a suit
export function getSuitIcon(suit: string): string {
  return SUIT_TO_ICON[suit as keyof typeof SUIT_TO_ICON] || 'help-circle';
}

// Gets the suit name
export function getSuitName(suit: string): string {
  return SUIT_TO_NAME[suit as keyof typeof SUIT_TO_NAME] || 'Unknown';
}

// Checks if a suit is red
export function isRedSuit(suit: string): boolean {
  return SUIT_TO_COLOR[suit as keyof typeof SUIT_TO_COLOR] === 'red';
} 