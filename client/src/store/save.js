import store from './base';

export function addPlantSave(plant) {
  store.save.push(plant);
}

export function updatePlantSave({
  position,
  plant,
  phase,
  watered,
  humidity,
}) {
  store.save = store.save.map((p) => {
    if (p.position.x === position.x && p.position.y === position.y) {
      return {
        position,
        plant,
        phase,
        watered,
        humidity,
      };
    }
    return p;
  });
}

export function deletePlantSave({ x, y }) {
  store.save = store.save.filter((plant) => plant.position.x !== x || plant.position.y !== y);
}

export async function loadGame(game) {
  store.save = game;
}
