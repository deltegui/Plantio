export default class Plant {
  static phaseLimit = 5;

  static dryState = 6;

  constructor({
    plant,
    position,
    phase,
    watered,
  }) {
    this.plantID = plant;
    this.sprite = null;
    this.phase = phase;
    this.position = position;
    this.watered = watered;
  }

  water() {
    if (this.phase < Plant.phaseLimit) {
      this.phase++;
      this.watered = 'wet';
    }
  }

  dry() {
    this.phase = Plant.dryState;
    this.watered = 'dry';
  }
}
