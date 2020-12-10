import store from './base';
import api from '../api';
import { reload } from '../game';

export function addPlantSave(plant) {
  store.save.push(plant);
  api.game.save(store.user.token, store.save);
}

export function updatePlantSave({ position, plant, phase }) {
  store.save = store.save.map((p) => {
    if (p.position.x === position.x && p.position.y === position.y) {
      return {
        position,
        plant,
        phase,
      };
    }
    return p;
  });
  api.game.save(store.user.token, store.save);
}

export async function loadGame() {
  store.save = await api.game.load(store.user.token);
  reload(store.save);
}
