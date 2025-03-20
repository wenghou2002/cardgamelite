import { createRouter, createWebHistory } from 'vue-router'
import GameView from '../views/GameView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'game',
      component: GameView,
      meta: { transition: 'fade' }
    }
  ]
})

export default router 