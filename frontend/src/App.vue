<template>
  <div class="min-h-screen flex flex-col bg-gray-50">
    <router-view v-slot="slotProps">
      <transition :name="slotProps.route.meta.transition as string || 'fade'" mode="out-in">
        <component :is="slotProps.Component" />
      </transition>
    </router-view>
  </div>
</template>

<script setup lang="ts">
// No need for complex script setup
</script>

<style>
:root {
  --primary-50: #f0f9ff;
  --primary-500: #0ea5e9;
  --primary-600: #0284c7;
  --danger-50: #fef2f2;
  --danger-600: #dc2626;
  --danger-700: #b91c1c;
}

html, body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
}

.bg-primary-50 { background-color: var(--primary-50); }
.bg-primary-500 { background-color: var(--primary-500); }
.bg-primary-600 { background-color: var(--primary-600); }
.bg-danger-50 { background-color: var(--danger-50); }

.border-primary-500 { border-color: var(--primary-500); }

.text-primary-500 { color: var(--primary-500); }
.text-primary-600 { color: var(--primary-600); }
.text-danger-600 { color: var(--danger-600); }
.text-danger-700 { color: var(--danger-700); }

.shadow-primary-200 { box-shadow: 0 0 5px rgba(14, 165, 233, 0.2); }

/* Page transitions */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.slide-enter-active,
.slide-leave-active {
  transition: all 0.3s ease-out;
}

.slide-enter-from {
  opacity: 0;
  transform: translateX(20px);
}

.slide-leave-to {
  opacity: 0;
  transform: translateX(-20px);
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

/* Card hover animations */
.card-container .w-12 {
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.card-container .w-12:hover {
  transform: translateY(-5px) scale(1.05);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  z-index: 10;
}

/* Shuffle button animation */
@keyframes pulse {
  0% { transform: scale(1); }
  50% { transform: scale(1.05); }
  100% { transform: scale(1); }
}

.shuffle-button:not(:disabled):hover {
  animation: pulse 1s infinite;
}

/* Deal animation */
@keyframes dealCard {
  from {
    opacity: 0;
    transform: translateY(-20px) rotate(-5deg);
  }
  to {
    opacity: 1;
    transform: translateY(0) rotate(0);
  }
}

.deal-animation {
  animation: dealCard 0.5s ease-out forwards;
}
</style> 