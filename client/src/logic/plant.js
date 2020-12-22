export default class Plant {
  static phaseLimit = 5;

  static dryState = 6;

  constructor({
    plant,
    position,
    phase,
    watered,
    humidity,
  }) {
    this.plant = plant;
    this.sprite = null;
    this.phase = phase;
    this.position = position;
    this.watered = watered;
    this.humidity = humidity;
  }

  water() {
    this.humidity = 100;
    this.watered = 'wet';
  }

  nextPhase() {
    if (this.phase < Plant.phaseLimit) {
      this.phase++;
    }
  }

  dry() {
    this.phase = Plant.dryState;
    this.watered = 'dry';
  }
}
