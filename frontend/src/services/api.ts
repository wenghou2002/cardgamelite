import axios from 'axios';
import { ShuffleDealResponse, EvaluateResponse } from '../models/Game';

const api = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Service to handle API calls to the game backend
export const gameApi = {
  // Gets shuffled cards from the server and returns the player hands
  shuffleAndDeal: async (): Promise<ShuffleDealResponse> => {
    try {
      const response = await api.get<ShuffleDealResponse>('/shuffle-deal');
      return response.data;
    } catch (error) {
      console.error('Error shuffling and dealing cards:', error);
      throw error;
    }
  },

  // Sends player hands to the server and returns who the winner is
  evaluateWinner: async (players: string[][]): Promise<EvaluateResponse> => {
    try {
      const response = await api.post<EvaluateResponse>('/evaluate', { players });
      return response.data;
    } catch (error) {
      console.error('Error evaluating winner:', error);
      throw error;
    }
  }
}; 