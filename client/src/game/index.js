/* eslint-disable */

import Phaser from 'phaser';
import Crop from './crop';
import PlantLoader from './plant_loader';
import MatrixIndex from './matrix_index';
import movable from './movable';

const entities = {
  Crop,
  MatrixIndex,
  PlantLoader,
};

const loadedEntities = {};

function preload() {
  Object.keys(entities).forEach((name) => {
    const e = new entities[name](this);
    loadedEntities[name] = e;
    e.load();
  });
}

function create() {
  Object.values(loadedEntities).forEach((entity) => entity.create());
  movable.startMoving();
}

function update() {
}

export default (canvas, { height, width } = { height: 680, width: 1000 }) => {
  const config = {
    type: Phaser.CANVAS,
    width,
    height,
    scene: {
      preload,
      create,
      update,
    },
    canvas,
    context: canvas.getContext('2d'),
    render: {
      transparent: true,
    },
  };
  return new Phaser.Game(config);
};

export function getPlantForPosition(position) {
  return loadedEntities.PlantLoader.getPlantForPosition(position);
}

export function addPlant(plantInfo) {
  return loadedEntities.PlantLoader.addPlant(plantInfo);
}

export function getAllPlants() {
  return loadedEntities.PlantLoader.plants;
}
