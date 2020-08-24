import Phaser from 'phaser';

export default class Flowerpot {
  static get size() {
    return {
      x: 117,
      y: 63,
    };
  }

  constructor(game, coordinate) {
    this.game = game;
    this.coordinate = coordinate;
    this.offset = new Phaser.Geom.Point(0, 30);
    this.movement = 10;
    this.shadow = null;
    this.sprite = null;
  }

  create() {
    this.position = this.coordinate.toRealPosition();
    console.log(this.position);
    this.createSprite();
  }

  createSprite() {
    this.sprite = this.game.add.sprite(this.position.x, this.position.y, 'mazeta').setOrigin(0, 0);
  }

  move() {
    this.sprite.y += this.movement;
    this.movement *= -1;
  }
}
