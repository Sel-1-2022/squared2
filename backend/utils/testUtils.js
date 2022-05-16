// Creates some test squares
const {SquareModel} = require("../models/SquareModel");
const {getIslandColorAndJoinLoops} = require("./islandUtils");
const {lonLatToId, TILE_DELTA} = require("./squareUtils");

const STERRE_GENT = [3.7112, 51.0238] //[3.7112, 51.0238] sterre Gent

// Draws a randomly colored grid
async function PopulateTestSquares() {
  for (let i = 0; i < 5; i++) {
    for (let j = 0; j < 5; j++) {
      const _id = lonLatToId(STERRE_GENT[0] + i * TILE_DELTA, STERRE_GENT[1] + j * TILE_DELTA);
      const color = Math.floor(Math.random() * 3)
      await new SquareModel({
        _id,
        color,
        island: await getIslandColorAndJoinLoops(color, STERRE_GENT[0] + i * TILE_DELTA, STERRE_GENT[1] + j * TILE_DELTA)
      }).save()
    }
  }
}

// Draws a 4x4 loop that should fill
async function PopulateTestSquaresWithLoop() {

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
    const _id = lonLatToId(STERRE_GENT[0] + i * TILE_DELTA, STERRE_GENT[1] + j * TILE_DELTA);
    const color = 7
    await new SquareModel({
      _id,
      color,
      island: await getIslandColorAndJoinLoops(color, STERRE_GENT[0] + i * TILE_DELTA, STERRE_GENT[1] + j * TILE_DELTA)
    }).save()
  }
}

// Draws a 4x4 loop that is not finished
async function PopulateTestSquaresWithLoopUnfinished4x4() {

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
    const _id = lonLatToId(STERRE_GENT[0] + i * TILE_DELTA, STERRE_GENT[1] + j * TILE_DELTA);
    const color = 7
    console.log(_id)
    await new SquareModel({
      _id,
      color,
      island: await getIslandColorAndJoinLoops(color, STERRE_GENT[0] + i * TILE_DELTA, STERRE_GENT[1] + j * TILE_DELTA).catch(console.log)
    }).save()
  }
}

// Draws a 3x3 loop that is not finished
async function PopulateTestSquaresWithLoopUnfinished3x3() {

  const is = [0, 0, 0,
    1,
    2, 2, 2]
  const js = [0, 1, 2,
    0,
    0, 1, 2]

  for (let id = 0; id < is.length; id++) {
    const i = is[id];
    const j = js[id];
    const _id = lonLatToId(STERRE_GENT[0] + i * TILE_DELTA, STERRE_GENT[1] + j * TILE_DELTA);
    const color = 7
    console.log(_id)
    await new SquareModel({
      _id,
      color,
      island: await getIslandColorAndJoinLoops(color, STERRE_GENT[0] + i * TILE_DELTA, STERRE_GENT[1] + j * TILE_DELTA).catch(console.log)
    }).save()
  }
}

// Draw an ascii art figure
async function DrawASCII(art){
  art = art.split(/\r?\n?\|/);
  for (let i = 0; i < art.length; i++) {
    const line = art[i].split('');
    for (let j = 0; j < line.length; j++) {
      const char = line[j];
      if (char !== '#') {
        const _id = lonLatToId(STERRE_GENT[0] + j * TILE_DELTA, STERRE_GENT[1] - i * TILE_DELTA);
        const color = parseInt(char, 10);
        await SquareModel.updateOne({_id}, {
          color,
          island: await getIslandColorAndJoinLoops(color, STERRE_GENT[0] + j * TILE_DELTA, STERRE_GENT[1] - i * TILE_DELTA).catch(console.log)
        }, {upsert: true})
      }
    }
  }
}

// Test an ascii art figure
async function TestASCII(art){
  art = art.split(/\r?\n?\|/);
  for (let i = 0; i < art.length; i++) {
    const line = art[i].split('');
    for (let j = 0; j < line.length; j++) {
      const char = line[j];
      const _id = lonLatToId(STERRE_GENT[0] + j * TILE_DELTA, STERRE_GENT[1] - i * TILE_DELTA);
      if (char !== '#') {
        const color = parseInt(char, 10);
        if(! await SquareModel.exists({_id, color})){
          console.log(`Tile ${_id} should have color ${color}.`)
          return false;
        }
      }
      else {
        if(await SquareModel.exists({_id})){
          console.log(`Tile ${_id} should not exist.`)
          return false;
        }
      }
    }
  }
  return true;
}

module.exports = {PopulateTestSquares, PopulateTestSquaresWithLoop, PopulateTestSquaresWithLoopUnfinished4x4, PopulateTestSquaresWithLoopUnfinished3x3, STERRE_GENT, DrawASCII, TestASCII}