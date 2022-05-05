// Creates some test squares
const {SquareModel} = require("../models/SquareModel");
const {getIslandColorAndJoinLoops} = require("./islandUtils");
const {lonLatToId, TILE_DELTA} = require("./squareUtils");

// Draws a randomly colored grid
async function PopulateTestSquares() {
  const center = [3.7112, 51.0238] //[3.7112, 51.0238] sterre Gent
  for (let i = 0; i < 5; i++) {
    for (let j = 0; j < 5; j++) {
      const _id = lonLatToId(center[0] + i * TILE_DELTA, center[1] + j * TILE_DELTA);
      const color = Math.floor(Math.random() * 3)
      await new SquareModel({
        _id,
        color,
        island: await getIslandColorAndJoinLoops(color, center[0] + i * TILE_DELTA, center[1] + j * TILE_DELTA)
      }).save()
    }
  }
}

// Draws a 4x4 loop that should fill
async function PopulateTestSquaresWithLoop() {
  const center = [3.7112, 51.0238] //[3.7112, 51.0238] sterre Gent

  const is = [0, 0, 0, 0,
    1, 1,
    2, 2,
    3, 3, 3, 3]
  const js = [0, 1, 2, 3,
    0, 3,
    0, 3,
    0, 1, 2, 3]

  for (let id = 0; id < is.length; id++) {
    const i = is[id];
    const j = js[id];
    const _id = lonLatToId(center[0] + i * TILE_DELTA, center[1] + j * TILE_DELTA);
    const color = 7
    await new SquareModel({
      _id,
      color,
      island: await getIslandColorAndJoinLoops(color, center[0] + i * TILE_DELTA, center[1] + j * TILE_DELTA)
    }).save()
  }
}

module.exports = {PopulateTestSquares, PopulateTestSquaresWithLoop}