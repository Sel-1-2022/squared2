const {STERRE_GENT} = require("../utils/testUtils");
const {lonLatToId, idToLonLat} = require("../utils/squareUtils");

test('lonLat to id and back to lonLat should be the same', () => {
  const testCenter = idToLonLat(lonLatToId(STERRE_GENT[0], STERRE_GENT[1]))
  expect(testCenter).toEqual(STERRE_GENT);
});

