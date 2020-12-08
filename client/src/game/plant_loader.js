import Entity from './entity';
import Plant from './plant';
import store from '../store';
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

  getPlantForPosition({ x, y }) {
    const matched = this.plants.filter(({ position }) => position.x === x && position.y === y);
    return matched.length === 0 ? false : matched[0];
  }

  load() {
    plantTypes.forEach(({ key, image }) => this.game.load.spritesheet(key, image, plantConfig));
  }

  create() {
    store.save.forEach(this.addPlant.bind(this));
  }

  addPlant(plantInfo) {
    const pl = new Plant(this.game, plantInfo);
    pl.create();
    this.plants.push(pl);
  }
}
