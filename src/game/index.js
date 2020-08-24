import Phaser from 'phaser';
import Crop from './crop';

let crop;

function preload() {
  crop = new Crop(this);
  crop.load();
  this.cameras.main.setBackgroundColor('#FFFFFF');
}

function create() {
  crop.create();
}

function update() {
}

export default (canvas, { height } = { height: 680 }) => {
  const config = {
    type: Phaser.CANVAS,
    width: 1000,
    height,
    scene: {
      preload,
      create,
      update,
    },
    canvas,
    context: canvas.getContext('2d'),
  };
  return new Phaser.Game(config);
};
