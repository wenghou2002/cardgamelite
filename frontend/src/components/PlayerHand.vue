<template>
  <div 
    class="border rounded-lg p-3 mb-3 relative transition-all duration-300"
    :class="{ 'border-primary-500 bg-primary-50 shadow-md': player.isWinner, 'border-gray-200 bg-gray-50': !player.isWinner }"
  >
    <h3 class="mt-0 mb-2 text-base font-medium text-gray-800">{{ player.name }}</h3>
    <div class="flex flex-wrap gap-1">
      <Card 
        v-for="(card, index) in player.hand" 
        :key="index" 
        :card="card"
        :highlight="player.isWinner"
        :matching="isMatchingCard(card)"
        :style="{ 'animation-delay': index * 0.1 + 's' }"
        class="card-animation"
      />
    </div>
    <div 
      v-if="player.isWinner" 
      class="absolute top-0 right-3 -translate-y-2.5 bg-primary-500 text-white px-2 py-0.5 rounded-full text-xs font-bold winner-badge"
    >
      Winner!
    </div>
    <div v-if="player.matchingValue && player.matchingCount && player.matchingCount > 1" class="flex items-center gap-2 text-xs mt-2 font-medium"
      :class="{ 'text-success-700': player.isWinner, 'text-gray-600': !player.isWinner }">
      <span :class="{ 'bg-success-100 text-success-800': player.isWinner, 'bg-gray-100 text-gray-700': !player.isWinner }" 
        class="px-2 py-1 rounded-full">{{ player.matchingCount }}</span> 
      matching <span class="font-bold">{{ player.matchingValue }}</span>'s
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineProps } from 'vue';
import { Player } from '../models/Player';
import Card from './Card.vue';
import { parseCard } from '../models/Card';

const props = defineProps<{
  player: Player;
}>();

// Check if a card is part of the matching set for this player
function isMatchingCard(card: string): boolean {
  if (!props.player.matchingValue) {
    return false;
  }
  
  const parsedCard = parseCard(card);
  return parsedCard.value === props.player.matchingValue;
}
</script>

<style scoped>
.card-animation {
  opacity: 0;
  animation: dealCard 0.3s ease-out forwards;
}

@keyframes dealCard {
  from {
    opacity: 0;
    transform: translateX(-20px) rotate(-5deg);
  }
  to {
    opacity: 1;
    transform: translateX(0) rotate(0);
  }
}

.winner-badge {
  animation: pulse 1.5s ease-in-out infinite;
}

@keyframes pulse {
  0% { transform: translateY(-10px) scale(1); }
  50% { transform: translateY(-10px) scale(1.1); }
  100% { transform: translateY(-10px) scale(1); }
}
</style> 