import gameService from './game.service';
import {
  InvalidXPosition,
  InvalidYPosition,
} from './input.errors';

export default {
  parseInputCoordinate({ x, y }) {
    const numy = parseInt(y, 10);
    if (isNaN(numy) || numy >= gameService.CropSize.y) {
      throw new InvalidYPosition(y);
    }
    switch (x) {
      case 'a': case 'A':
        return { x: numy, y: 3 };
      case 'b': case 'B':
        return { x: numy, y: 2 };
      case 'c': case 'C':
        return { x: numy, y: 1 };
      case 'd': case 'D':
        return { x: numy, y: 0 };
      default:
        throw new InvalidXPosition(x);
    }
  },

  passNumberToLetter(number) {
    const letters = ['D', 'C', 'B', 'A'];
    return letters[number];
  },

  passGameEventTypeToMessage(eventType) {
    switch (eventType) {
      case 'GROW':
        return 'have grown.';
      case 'KILLED_TEMPERATURE_LOW':
        return 'died because temperature is too low to live.';
      case 'KILLED_TEMPERATURE_HIGH':
        return 'died because temperature is too high to live.';
      case 'KILLED_DRY':
        return 'died because is dry.';
      case 'WATERED':
        return 'is watered';
      case 'DRIED':
        return 'is dried';
      default: return 'is unkown';
    }
  },
};
