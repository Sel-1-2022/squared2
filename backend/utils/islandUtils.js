// Test if island exists
const {SquareModel} = require("../models/SquareModel");
const {lonLatToId, TILE_DELTA, roundToTileDelta, idToLonLat} = require("./squareUtils");

async function IslandExists(island, color) {
  return SquareModel.exists({island, color})
}

// Random integer
function getRndInteger(min, max) {
  return Math.floor(Math.random() * (max - min)) + min;
}

// Get a free island for color
async function freeIslandForColor(color) {
  let island = getRndInteger(0, 10000)
  while (await IslandExists(island, color)) {
    island = getRndInteger(0, 10000)
  }
  return island
}

// Get neighbouring lon lat
function getNeighbouringLonLat(lon, lat) {
  const lonLatList = []
  for (let i = -1; i < 2; i++) {
    for (let j = -1; j < 2; j++) {
      if (Math.abs(i) !== Math.abs(j)) {
        const newLon = roundToTileDelta(lon + TILE_DELTA * j);
        const newLat = roundToTileDelta(lat + TILE_DELTA * i);
        lonLatList.push([newLon, newLat]);
      }
    }
  }
  return lonLatList
}

// Get neighbour id don't have certain color
async function getNeighbouringIdWithoutColor(lon, lat, color) {
  console.log(lon)
  console.log(lat)
  const neighbourLonLat = getNeighbouringLonLat(lon, lat)
  const ids = []
  for (const coords of neighbourLonLat) {
    let tempId = lonLatToId(coords[0], coords[1])
    let neighbourColored = await SquareModel.findOne({"_id": tempId, color})
    if (neighbourColored === null) {
      ids.push(tempId)
    }
  }
  return ids
}

// Get diagonally neighbouring lon lat
function getDiagonallyNeighbouringLonLat(lon, lat) {
  const lonLatList = []
  for (let i = -1; i < 2; i++) {
    for (let j = -1; j < 2; j++) {
      if (Math.abs(i) === Math.abs(j) && i !== 0) {
        const newLon = roundToTileDelta(lon + TILE_DELTA * j);
        const newLat = roundToTileDelta(lat + TILE_DELTA * i);
        lonLatList.push([newLon, newLat]);
      }
    }
  }
  return lonLatList
}


// Get tile North
async function getNorthNeighbour(lon, lat) {
  return SquareModel.findOne({"_id": lonLatToId(lon, roundToTileDelta(lat + TILE_DELTA))})
}

// Get tile East
async function getEastNeighbour(lon, lat) {
  return SquareModel.findOne({"_id": lonLatToId(roundToTileDelta(lon + TILE_DELTA), lat)})
}

// Get tile South
async function getSouthNeighbour(lon, lat) {
  return SquareModel.findOne({"_id": lonLatToId(lon, roundToTileDelta(lat - TILE_DELTA))})
}

// Get tile West
async function getWestNeighbour(lon, lat) {
  return SquareModel.findOne({"_id": lonLatToId(roundToTileDelta(lon - TILE_DELTA), lat)})
}

// TODO handle breaking up of islands
// Logic for what island to join and looping
async function getIslandColorAndJoinLoops(color, lon, lat) {
  const N = await getNorthNeighbour(lon, lat)
  const E = await getEastNeighbour(lon, lat)
  const S = await getSouthNeighbour(lon, lat)
  const W = await getWestNeighbour(lon, lat)

  const allNeighboursOfSameColor = [N, E, S, W].filter(a => {
    return a && a.color === color // All neighbours that have the same color and exist
  })

  if (allNeighboursOfSameColor.length > 0) { // If atleast one neighbour has the same color
    const focusIsland = allNeighboursOfSameColor[0].island; // We will take the first neighbours color as focus
    const islandsToUpdate = []; // These islands will have there islands changed to the focus after the looping algo is done
    const possiblyMoreThenOneLoop = false; // Check the extreme unlikely case that two loops have formed

    if (allNeighboursOfSameColor.length > 1) {
      for (let i = 1; i < allNeighboursOfSameColor.length; i++) {
        if (allNeighboursOfSameColor[i].island !== focusIsland) { // If one of the neighbours has a different island then make it join the focussed island
          islandsToUpdate.push(allNeighboursOfSameColor[i].island)
        }
      }
    }

    if (allNeighboursOfSameColor.length > 1) {
      let startLonLat = new Set(getDiagonallyNeighbouringLonLat(lon, lat).map(lonLat => lonLatToId(lonLat[0], lonLat[1]))) // Get enough starting points by taking the diagonal neighbours, this gives 4 starting points instead of 2, to be fixed
      allNeighboursOfSameColor.forEach(square => { // Filter the ones with same color
        startLonLat.delete(square._id)
      })
      await findLoops([...startLonLat], color, focusIsland);
    }


    for (let i = 0; i < islandsToUpdate.length; i++) {
      const island = islandsToUpdate[i];
      await SquareModel.updateMany({
        island,
        color
      }, {
        island: focusIsland
      })
    }

    return focusIsland; // Return the color of the new square that started this
  }
  return freeIslandForColor(color); // Otherwise get a new island
}

// Try to fill loop starting from given squares with given id
async function findLoops(startList, color, island) {
  let parkedSets = []
  let activeSets = []
  let found = false
  for (const start of startList) {
    parkedSets.push(new Set())
    let newSet = new Set()
    newSet.add(start)
    activeSets.push(newSet)
    console.log('Start: ' + start)
  }

  while (!found) {
    for (let i = 0; i < 1; i++) {
      console.log(i)
      await findLoopStep(parkedSets[i], activeSets[i], color) // Move one step forward
      if (activeSets[i].size === 0) {
        found = true;
        for (const parked of parkedSets[i]) {
          await SquareModel.updateOne(
            {'_id': parked},
            {color, island},
            {upsert: true})
        }
      }

      break;
    }

  }
}

async function findLoopStep(parked, active, color) {
  const activeCopy = [...active]
  for (const activeId of activeCopy) {
    let lonLat = idToLonLat(activeId);
    let toAdd = await getNeighbouringIdWithoutColor(lonLat[0], lonLat[1], color)
    toAdd.forEach(a => active.add(a))
  }
  activeCopy.forEach(a => {
    parked.add(a)
    active.delete(a)
  })
  parked.forEach((a, b, c) => {
    active.delete(a)
  })
  console.log(parked)
  console.log(active)
}

module.exports = {getIslandColorAndJoinLoops}