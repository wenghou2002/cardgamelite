<template>
  <div 
    class="w-12 h-18 rounded-md border bg-white shadow-sm flex flex-col justify-center items-center m-1 transition-all duration-200 relative card-flip"
    :class="{ 
      'border-primary-500 shadow-md shadow-primary-200 -translate-y-0.5': highlight && !matching, 
      'border-success-500 shadow-md shadow-success-200 -translate-y-1 border-2': matching,
      'border-gray-300': !highlight && !matching 
    }"
  >
    <div class="card-inner">
      <div class="card-front">
        <div 
          class="text-base font-bold mb-0.5"
          :class="{ 
            'text-black': !isRedSuit && !matching, 
            'text-danger-600': isRedSuit && !matching,
            'text-success-600': matching
          }"
        >
          {{ cardValue }}
        </div>
        <div 
          :class="{ 
            'text-black': !isRedSuit && !matching, 
            'text-danger-600': isRedSuit && !matching,
            'text-success-600': matching
          }"
        >
          <component :is="suitIcon" class="w-4 h-4" />
        </div>
      </div>
      <div class="card-back">
        <div class="grid grid-cols-2 grid-rows-2 gap-0.5">
          <div class="w-3 h-3 bg-primary-500 rounded-full opacity-80"></div>
          <div class="w-3 h-3 bg-primary-600 rounded-full opacity-80"></div>
          <div class="w-3 h-3 bg-primary-600 rounded-full opacity-80"></div>
          <div class="w-3 h-3 bg-primary-500 rounded-full opacity-80"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { parseCard } from '../models/Card';
import { getSuitIcon, isRedSuit as checkIsRedSuit } from '../utils/cardUtils';
import { Club, Diamond, Heart, Spade } from 'lucide-vue-next';

// Register Lucide icons dynamically
const suitIcons = {
  'club': Club,
  'diamond': Diamond,
  'heart': Heart,
  'spade': Spade
};

const props = defineProps<{
  card: string;
  highlight?: boolean;
  matching?: boolean;
}>();

// Parse the card string into value and suit
const cardData = computed(() => parseCard(props.card));
const cardValue = computed(() => cardData.value.value);
const cardSuit = computed(() => cardData.value.suit);

// Get the Lucide icon component for this suit
const suitIcon = computed(() => {
  const iconName = getSuitIcon(cardSuit.value);
  return suitIcons[iconName as keyof typeof suitIcons];
});

// Determine if this is a red suit
const isRedSuit = computed(() => checkIsRedSuit(cardSuit.value));
</script>

<style scoped>
.h-18 {
  height: 4rem;
}

.w-12 {
  width: 2.75rem;
}

.card-flip {
  perspective: 1000px;
}

.card-inner {
  position: relative;
  width: 100%;
  height: 100%;
  text-align: center;
  transition: transform 0.6s;
  transform-style: preserve-3d;
}

.card-flip:hover .card-inner {
  transform: rotateY(180deg);
}

.card-front, .card-back {
  position: absolute;
  width: 100%;
  height: 100%;
  -webkit-backface-visibility: hidden;
  backface-visibility: hidden;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.card-front {
  z-index: 2;
}

.card-back {
  transform: rotateY(180deg);
  background-color: white;
}
</style> 