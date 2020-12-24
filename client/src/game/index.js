/* eslint-disable */

import Phaser from 'phaser';
import Crop from './crop';
import PlantLoader from './plant_loader';
import MatrixIndex from './matrix_index';
import movable from './movable';
import CropCoordinate from './coordinate';
import types from './plant_types';

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

export const createGame = (canvas, { height, width } = { height: 680, width: 1000 }) => {
  const config = {
    type: Phaser.CANVAS,
    width,
    height,
    scene: {
      preload,
      create,
      update,
    },
    fps: {
      forceSetTimeOut: true,
      target: 7,
    },
    canvas,
    context: canvas.getContext('2d'),
    render: {
      transparent: true,
    },
  };
  return new Phaser.Game(config);
};

function getPlantForPosition(position) {
  return loadedEntities.PlantLoader.getPlantForPosition(position);
}

export function emphasisPlant(position) {
  const plant = getPlantForPosition(position);
  if (!plant) return;
  plant.emphasis();
}

export function stopMoving() {
  movable.stopMoving();
}

export function startMoving() {
  movable.startMoving();
}

export function reload(plants) {
  const sortedPlants = plants.sort((first, second) => {
    if (first.position.x < second.position.x) {
      return -1;
    }
    if (first.position.x > second.position.x) {
      return 1;
    }
    if (first.position.y < second.position.y) {
      return -1;
    }
    if (first.position.y > second.position.y) {
      return 1;
    }
    return 0;
  });
  loadedEntities.Crop.reload(plants);
  loadedEntities.PlantLoader.reloadPlants(sortedPlants);
}

export const CropSize = {
  x: CropCoordinate.MaxSize.x,
  y: CropCoordinate.MaxSize.x,
};

export const plantTypes = types;
