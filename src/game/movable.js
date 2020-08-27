class Movable {
  constructor() {
    this.movement = 10;
    this.callbacks = [];
  }

  add(callback) {
    this.callbacks.push(callback);
  }

  startMoving() {
    setInterval(() => {
      this.callbacks.forEach((fn) => fn(this.movement));
      this.movement *= -1;
    }, 1000);
  }
}

export default new Movable();
