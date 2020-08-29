import Entity from './entity';
import movable from './movable';

const indexes = [
  { x: 48, y: 330, text: 'A' },
  { x: 150, y: 270, text: 'B' },
  { x: 270, y: 210, text: 'C' },
  { x: 390, y: 150, text: 'D' },
  { x: 590, y: 150, text: '0' },
  { x: 710, y: 210, text: '1' },
  { x: 830, y: 270, text: '2' },
  { x: 930, y: 330, text: '3' },
];

export default class MatrixIndex extends Entity {
  constructor(game) {
    super();
    this.game = game;
    this.texts = [];
  }

  create() {
    this.texts = indexes.map(this.createText.bind(this));
    movable.add(this.move.bind(this));
  }

  createText({ x, y, text }) {
    return this.game.add.text(
      x,
      y,
      text,
      {
        fontFamily: 'consoles',
        fontSize: '25px',
      },
    );
  }

  move(movement) {
    for (let i = 0; i < this.texts.length; i++) {
      this.texts[i].y += movement;
    }
  }
}
