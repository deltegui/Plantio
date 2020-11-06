import CropCoordinate from '../../../src/game/coordinate';

describe('CropCoordinate', () => {
  describe('toRealPosition', () => {
    it('should transform a cropcoordinate to real pixel position', () => {
      const coordinate = new CropCoordinate(1, 3);
      const expectedPosition = { x: 0, y: 0 };
      expect(coordinate.toRealPosition()).toBeEql(expectedPosition);
    });
  });
});
