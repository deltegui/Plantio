import store from './base';
import api from '../api';
import { reload } from '../game';

export function addPlantSave(plant) {
  store.save.push(plant);
  reload(store.save);
}

export function updatePlantSave({
  position,
  plant,
  phase,
  watered,
}) {
  store.save = store.save.map((p) => {
    if (p.position.x === position.x && p.position.y === position.y) {
      return {
        position,
        plant,
        phase,
        watered,
      };
    }
    return p;
  });
  reload(store.save);
}

export function deletePlantSave({ x, y }) {
  store.save = store.save.filter((plant) => plant.position.x !== x || plant.position.y !== y);
  reload(store.save);
}

export async function loadGame() {
  store.save = await api.game.load(store.user.token);
  reload(store.save);
}
