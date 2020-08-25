import Phaser from 'phaser';
import Crop from './crop';

let crop;

function preload() {
  crop = new Crop(this);
  crop.load();
}

function create() {
  crop.create();
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
