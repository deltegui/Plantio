import Phaser from 'phaser';

export default class Flowerpot {
  static get size() {
    return {
      x: 117,
      y: 63,
    };
  }

  constructor(game, coordinate, options = { watered: false }) {
    this.game = game;
    this.coordinate = coordinate;
    this.offset = new Phaser.Geom.Point(0, 30);
    this.shadow = null;
    this.sprite = null;
    this.watered = options.watered;
  }

  create() {
    this.position = this.coordinate.toRealPosition();
    this.createSprite();
  }

  createSprite() {
    if (this.watered) {
      this.sprite = this.game.add.sprite(this.position.x, this.position.y, 'mazeta').setOrigin(0, 0);
    } else {
      this.sprite = this.game.add.sprite(this.position.x, this.position.y, 'mazeta_seca').setOrigin(0, 0);
    }
  }

  move(movement) {
    this.sprite.y += movement;
  }

  destroy() {
    this.sprite.destroy();
  }
}
