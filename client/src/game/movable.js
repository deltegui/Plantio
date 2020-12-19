class Movable {
  constructor() {
    this.movement = 10;
    this.callbacks = [];
    this.interval = null;
  }

  add(callback) {
    this.callbacks.push(callback);
  }

  startMoving() {
    this.interval = setInterval(() => {
      this.callbacks.forEach((fn) => fn(this.movement));
      this.movement *= -1;
    }, 1000);
  }

  stopMoving() {
    if (!this.interval) return;
    clearInterval(this.interval);
  }
}

export default new Movable();
