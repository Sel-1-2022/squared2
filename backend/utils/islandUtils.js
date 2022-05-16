DISTANCE_LIMIT = 8 // How far to form a loop

// Test if island exists
const {SquareModel} = require("../models/SquareModel");
const {lonLatToId, TILE_DELTA, roundToTileDelta, idToLonLat} = require("./squareUtils");
const {start} = require("../app");

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
async function getNeighbouringForFill(lon, lat, color) {
  let neighbourLonLat = getNeighbouringLonLat(lon, lat)
  neighbourLonLat = neighbourLonLat.concat(getDiagonallyNeighbouringLonLat(lon, lat))
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


// Logic for what island to join and looping
async function getIslandColorAndJoinLoops(color, lon, lat) {
  const neighbourIDs = getNeighbouringLonLat(lon, lat).map(loc => lonLatToId(loc[0], loc[1]))
  const neighboursSquares = await SquareModel.find({'_id': {$in: neighbourIDs}})
  const diagNeighbourIDs = getDiagonallyNeighbouringLonLat(lon, lat).map(loc => lonLatToId(loc[0], loc[1]))
  const diagNeighbourSquares = await SquareModel.find({'_id': {$in: diagNeighbourIDs}})

  const neighbourColorCount = {} // Count how many neighbours of each island we have that is different colored
  neighboursSquares.forEach(square => {
    if (color !== square.color) {
      const code = square.color.toString() + ',' + square.island.toString()

      if (code in neighbourColorCount) {
        neighbourColorCount[code].push(square);
      } else {
        neighbourColorCount[code] = [square];
      }
    }
  })
  for (const [key, value] of Object.entries(neighbourColorCount)) {
    if (value.length >= 2) { // If we have two neighbours with the same island we probably just split an island TODO >2 is not very exact
      await value.shift();
      for(const a of value){
        await recalculateIsland(a.id, a.color, lonLatToId(lon, lat))
      }
    }
  }


  const allNeighboursOfSameColor = neighboursSquares.filter(a => {
    return a?.color === color // All neighbours that have the same color and exist
  })

  let islandCount = {}
  allNeighboursOfSameColor.forEach(tile => {
    if (!(tile.island in islandCount)) {
      islandCount[tile.island] = 1;
    } else {
      islandCount[tile.island] += 1;
    }
  })

  const checkForLoop = Math.max(Object.values(islandCount)) >= 2

  if (allNeighboursOfSameColor.length > 0) { // If at least one neighbour has the same color
    const focusIsland = allNeighboursOfSameColor[0].island; // We will take the first neighbours color as focus
    const islandsToUpdate = []; // These islands will have there islands changed to the focus after the looping algo is done
    const possiblyMoreThenOneLoop = false; // TODO Check the extreme unlikely case that two loops have formed

    if (allNeighboursOfSameColor.length > 1) {
      for (let i = 1; i < allNeighboursOfSameColor.length; i++) {
        if (allNeighboursOfSameColor[i].island !== focusIsland) { // If one of the neighbours has a different island then make it join the focussed island
          islandsToUpdate.push(allNeighboursOfSameColor[i].island)
        }
      }
    }


    if (checkForLoop) {
      let startIds = new Set(diagNeighbourIDs) // Get enough starting points by taking the diagonal neighbours, this gives 4 starting points instead of 2, to be fixed
      neighbourIDs.forEach(a => startIds.add(a))
      for (const id of startIds) {
        let temp = await SquareModel.findOne({"_id": id})
        if (temp?.color === color) {
          startIds.delete(id)
        }
      }
      await findLoops([...startIds], color, focusIsland, lonLatToId(lon, lat));
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
async function findLoops(startList, color, island, thisId) {
  console.log("Finding loop")
  console.log(thisId)
  let parkedSets = []
  let startSet = new Set()
  let activeSets = []
  let found = false
  let dis = 0
  for (const start of startList) {
    startSet.add(start)
    parkedSets.push(new Set())
    let newSet = new Set()
    newSet.add(start)
    activeSets.push(newSet)
  }

  while (!found) {
    for (let i = 0; i < parkedSets.length; i++) {
      const contThisStart = await findLoopStep(parkedSets[i], activeSets[i], color, thisId, startSet, startList[i]) // Move one step forward
      if (!contThisStart) {
        parkedSets.splice(i, 1);
        activeSets.splice(i, 1);
        startList.splice(i, 1);
        i -= 1;
      }
      if (contThisStart && activeSets[i].size === 0) {
        found = true;
        for (const parked of parkedSets[i]) {
          await SquareModel.updateOne(
            {'_id': parked},
            {color, island},
            {upsert: true})
        }
        break;
      }
    }
    dis++;
    if (dis > DISTANCE_LIMIT) {
      console.log("LIMIT REACHED, stop loop search")
      console.log(thisId)
      break;
    }
  }
}

async function findLoopStep(parked, active, color, thisId, starts, thisStart) {
  const activeCopy = [...active]
  active.clear();
  let cont = true;
  for (const activeId of activeCopy) {
    let lonLat = idToLonLat(activeId);
    let toAdd = await getNeighbouringForFill(lonLat[0], lonLat[1], color)
    toAdd.forEach((a, b, c) => active.add(a))
  }
  active.delete(thisId);
  activeCopy.forEach(a => {
    parked.add(a)
    active.delete(a)
  })
  parked.forEach((a, b, c) => {
    active.delete(a)
  })
  active.forEach((a, b, c) => {
    if (starts.has(a)) {
      starts.delete(thisStart);
      cont = false;
    }
  })
  return cont;
}

// Get Neighbours with same color and island
async function getNeighboursWithSameColor(id, color) {
  const [lon, lat] = idToLonLat(id);
  const neighbourIDs = getNeighbouringLonLat(lon, lat).map(pos => lonLatToId(pos[0], pos[1]));
  return (await SquareModel.find({'_id': {$in: neighbourIDs}, color})).map(a => a.id)
}

// Checks if this splits an island
async function recalculateIsland(start, color, exclude) {
  const newIsland = await freeIslandForColor(color);
  const old = new Set()
  let next = new Set([start])
  while (next.size > 0) {
    let new_next = []
    for (const nextSquare of next) {
      await SquareModel.updateOne({"_id": nextSquare}, {island: newIsland})
      new_next = new_next.concat(await getNeighboursWithSameColor(nextSquare, color));
      old.add(nextSquare);
    }
    new_next = new Set(new_next)
    old.forEach(olds => {
      new_next.delete(olds);
    })
    new_next.delete(exclude);
    next = new_next;
  }
}

module.exports = {getIslandColorAndJoinLoops}