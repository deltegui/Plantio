import Entity from './entity';
import Plant from './plant';
import plantTypes from './plant_types';

const plantConfig = {
  frameWidth: 189,
  frameHeight: 279,
};

export default class PlantLoader extends Entity {
  constructor(game) {
    super();
    this.game = game;
    this.plants = [];
  }

  load() {
    plantTypes.forEach(({ key, image }) => this.game.load.spritesheet(key, image, plantConfig));
  }

  getPlantForPosition({ x, y }) {
    const matched = this.plants.filter(({ position }) => position.x === x && position.y === y);
    return matched.length === 0 ? false : matched[0];
  }

  addPlant(plantInfo) {
    const pl = new Plant(this.game, plantInfo);
    pl.create();
    this.plants.push(pl);
  }

  reloadPlants(next) {
    this.plants.forEach((p) => p.destroy());
    this.plants = [];
    next.forEach(this.addPlant.bind(this));
  }
}
