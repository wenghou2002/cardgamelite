<template>
  <div class="max-w-5xl mx-auto p-3 sm:p-4 animate-fade-in">
    <div class="flex justify-between items-center mb-3">
      <h1 class="text-xl font-bold text-gray-800">Card Game</h1>
      
      <div class="flex gap-2">
        <button 
          v-if="players.length > 0"
          class="bg-primary-500 text-white border-none rounded-md px-4 py-1.5 text-sm cursor-pointer transition-colors duration-300 hover:bg-primary-600 disabled:bg-primary-200 disabled:cursor-not-allowed shuffle-button"
          @click="handleShuffleAndDeal" 
          :disabled="isLoading"
        >
          {{ isLoading ? 'Loading...' : 'Shuffle & Deal' }}
        </button>
        
        <button
          v-if="players.length > 0"
          class="bg-gray-200 text-gray-800 border-none rounded-md px-4 py-1.5 text-sm cursor-pointer transition-colors duration-300 hover:bg-gray-300"
          @click="goHome"
        >
          ← Home
        </button>
      </div>
    </div>
    
    <div v-if="error" class="bg-danger-50 text-danger-700 p-2 rounded mb-3 text-center text-sm">
      {{ error }}
    </div>
    
    <!-- Welcome screen when no cards are dealt yet -->
    <div v-if="players.length === 0" class="bg-white rounded-lg shadow-md p-6 mb-4">
      <div class="text-center mb-6">
        <h2 class="text-2xl font-bold text-primary-600 mb-2">Welcome to the Card Game!</h2>
        <p class="text-gray-600">Press the "Start Game" button to begin playing.</p>
      </div>
      
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
        <div class="bg-gray-50 p-4 rounded-lg border border-gray-200">
          <h3 class="font-semibold text-lg mb-2 text-gray-800 flex items-center">
            <span class="bg-primary-100 text-primary-800 w-6 h-6 rounded-full inline-flex items-center justify-center mr-2 text-sm">1</span>
            Cards
          </h3>
          <p class="text-sm text-gray-600 mb-2">52 cards with different values and symbols:</p>
          <div class="flex flex-wrap gap-1 mb-2">
            <div v-for="value in ['2','3','A','K']" :key="value" class="w-8 h-10 bg-white border border-gray-300 rounded shadow-sm flex items-center justify-center text-xs font-medium">
              {{value}}
            </div>
            <div class="text-xs mt-1">+ more...</div>
          </div>
          <p class="text-xs text-gray-500">Values: 2-10, J, Q, K, A</p>
          <div class="flex flex-wrap gap-3 mt-2 justify-start">
            <div class="flex items-center bg-gray-100 px-2 py-1 rounded">
              <Club class="w-4 h-4 mr-1" /> 
              <span class="text-xs">Clubs (@)</span>
            </div>
            <div class="flex items-center bg-gray-100 px-2 py-1 rounded">
              <Diamond class="w-4 h-4 text-danger-600 mr-1" /> 
              <span class="text-xs">Diamonds (#)</span>
            </div>
            <div class="flex items-center bg-gray-100 px-2 py-1 rounded">
              <Heart class="w-4 h-4 text-danger-600 mr-1" /> 
              <span class="text-xs">Hearts (^)</span>
            </div>
            <div class="flex items-center bg-gray-100 px-2 py-1 rounded">
              <Spade class="w-4 h-4 mr-1" /> 
              <span class="text-xs">Spades (*)</span>
            </div>
          </div>
        </div>
        
        <div class="bg-gray-50 p-4 rounded-lg border border-gray-200">
          <h3 class="font-semibold text-lg mb-2 text-gray-800 flex items-center">
            <span class="bg-primary-100 text-primary-800 w-6 h-6 rounded-full inline-flex items-center justify-center mr-2 text-sm">2</span>
            How to Win
          </h3>
          <p class="text-sm text-gray-600 mb-2">The winner is the player with:</p>
          <ul class="text-sm text-gray-600 list-disc pl-5 space-y-1">
            <li>Most cards with the same value (e.g., three K's)</li>
            <li>In case of a tie, higher card value wins</li>
            <li>If still tied, higher symbol value wins 
              <span class="items-center gap-1 inline-flex text-xs mt-1">
                <Spade class="w-3 h-3" /> > 
                <Heart class="w-3 h-3 text-danger-600" /> > 
                <Diamond class="w-3 h-3 text-danger-600" /> > 
                <Club class="w-3 h-3" />
              </span>
            </li>
          </ul>
          <p class="text-xs text-gray-500 mt-2">Example: 
            <span class="font-medium">K<Club class="w-3 h-3 inline-block" />, K<Diamond class="w-3 h-3 inline-block text-danger-600" />, K<Heart class="w-3 h-3 inline-block text-danger-600" /></span> 
            beats 
            <span class="font-medium">J<Club class="w-3 h-3 inline-block" />, J<Diamond class="w-3 h-3 inline-block text-danger-600" />, J<Heart class="w-3 h-3 inline-block text-danger-600" /></span>
          </p>
        </div>
      </div>
      
      <div class="text-center">
        <button 
          class="bg-primary-500 text-white border-none rounded-md px-6 py-2 text-base cursor-pointer transition-colors duration-300 hover:bg-primary-600 disabled:bg-primary-200 disabled:cursor-not-allowed"
          @click="handleShuffleAndDeal" 
          :disabled="isLoading"
        >
          {{ isLoading ? 'Loading...' : 'Start Game' }}
        </button>
      </div>
    </div>
    
    <!-- Title showing winner -->
    <transition name="bounce">
      <div v-if="winnerIndex !== null" class="text-center p-2 bg-primary-50 rounded-lg mb-3">
        <h2 class="text-lg font-bold">Winner: {{ players[winnerIndex].name }}</h2>
      </div>
    </transition>
    
    <!-- Game table -->
    <div v-if="players.length > 0" class="bg-white rounded-lg shadow-sm p-2 mb-3">
      <div class="text-sm font-medium text-gray-600 mb-1">Game Table</div>
      
      <!-- Reordered player list - winner first if there is one -->
      <div class="grid grid-cols-1 sm:grid-cols-2 gap-2 card-container">
        <template v-if="players.length > 0">
          <!-- Winner goes first if there is one -->
          <PlayerHand 
            v-if="winnerIndex !== null"
            :player="players[winnerIndex]"
            class="sm:col-span-2 deal-animation"
            style="animation-delay: 0s"
          />
          
          <!-- Other players -->
          <PlayerHand 
            v-for="(player, index) in nonWinningPlayers" 
            :key="player.id" 
            :player="player"
            :class="{ 'deal-animation': true }"
            :style="{ 'animation-delay': index * 0.15 + 's' }"
          />
        </template>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';
import { Player, findHighestMatchingValue } from '../models/Player';
import { gameApi } from '../services/api';
import PlayerHand from '../components/PlayerHand.vue';
import { Club, Diamond, Heart, Spade } from 'lucide-vue-next';

// Game state
const players = ref<Player[]>([]);
const isLoading = ref<boolean>(false);
const error = ref<string | null>(null);
const winnerIndex = ref<number | null>(null);

// Filter out the winning player to display others separately
const nonWinningPlayers = computed(() => {
  if (winnerIndex.value === null) {
    return players.value;
  }
  return players.value.filter((_, index) => index !== winnerIndex.value);
});

// Returns to home screen
function goHome() {
  players.value = [];
  error.value = null;
  winnerIndex.value = null;
}

// Handles shuffling and dealing cards, then finds the winner through API calls
const handleShuffleAndDeal = async () => {
  isLoading.value = true;
  error.value = null;
  winnerIndex.value = null;
  
  try {
    // Step 1: Shuffle and deal cards
    const response = await gameApi.shuffleAndDeal();
    
    // Step 2: Format the response into players
    players.value = response.players.map((hand, index) => {
      // Find the highest matching cards for each player
      const { value, count } = findHighestMatchingValue(hand);
      
      return {
        id: index,
        name: `Player ${index + 1}`,
        hand,
        isWinner: false,
        matchingValue: value,
        matchingCount: count
      };
    });
    
    // Step 3: Evaluate the winner
    const evalResponse = await gameApi.evaluateWinner(response.players);
    winnerIndex.value = evalResponse.winner;
    
    // Step 4: Mark the winner
    if (winnerIndex.value !== null && players.value[winnerIndex.value]) {
      players.value[winnerIndex.value].isWinner = true;
    }
    
  } catch (err) {
    console.error('Game error:', err);
    error.value = 'Failed to communicate with the server. Please try again.';
  } finally {
    isLoading.value = false;
  }
};
</script>

<style scoped>
.animate-fade-in {
  animation: fadeIn 0.5s ease-in forwards;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

/* Bounce animation for winner announcement */
.bounce-enter-active {
  animation: bounce-in 0.5s;
}
.bounce-leave-active {
  animation: bounce-in 0.5s reverse;
}
@keyframes bounce-in {
  0% {
    transform: scale(0);
  }
  50% {
    transform: scale(1.1);
  }
  100% {
    transform: scale(1);
  }
}
</style> 