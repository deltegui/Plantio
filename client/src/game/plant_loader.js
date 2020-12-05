import Entity from './entity';
import Plant from './plant';

const plantTypes = [
  {
    key: 'wheat',
    image: 'wheat.png',
  },
];

const plantConfig = {
  frameWidth: 189,
  frameHeight: 279,
};

const saved = [
  {
    position: { x: 0, y: 0 },
    plant: 'wheat',
    phase: 3,
  },
  {
    position: { x: 3, y: 3 },
    plant: 'wheat',
    phase: 2,
  },
  {
    position: { x: 2, y: 3 },
    plant: 'wheat',
    phase: 5,
  },
  {
    position: { x: 2, y: 2 },
    plant: 'wheat',
    phase: 4,
  },
  {
    position: { x: 0, y: 3 },
    plant: 'wheat',
    phase: 1,
  },
];

export default class PlantLoader extends Entity {
  constructor(game) {
    super();
    this.game = game;
    this.plants = [];
  }

  load() {
    plantTypes.forEach(({ key, image }) => this.game.load.spritesheet(key, image, plantConfig));
  }

  create() {
    saved.forEach(this.addPlant.bind(this));
  }

  addPlant(plantInfo) {
    const pl = new Plant(this.game, plantInfo);
    pl.create();
    this.plants.push(pl);
  }
}
