/* eslint-disable */

import Phaser from 'phaser';
import Crop from './crop';
import MatrixIndex from './matrix_index';
import movable from './movable';

const entities = [
  Crop,
  MatrixIndex,
];

const loadedEntities = [];

function preload() {
  entities.forEach((Entity) => {
    const e = new Entity(this);
    loadedEntities.push(e);
    e.load();
  });
}

function create() {
  loadedEntities.forEach((entity) => entity.create());
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
