const {SquareModel} = require("../models/SquareModel");

const TILE_DELTA = 0.0001 // 7 dec + sign per lat or lon DONT CHANGE THIS

// Convert coordinates to id
function latLonToId(lat, lon) {
  const latSign = (Math.sign(lat) > 0 ? 1 : 0).toString() // character 0
  let latPart = Math.abs(Math.round(lat / TILE_DELTA)).toString() // character 1 to 7
  const lonSign = (Math.sign(lon) > 0 ? 1 : 0).toString() // character 8
  let lonPart = Math.abs(Math.round(lon / TILE_DELTA)).toString() // character 9 to 16

  while (latPart.length < 7) { // adding leading zeros to maintain length
    latPart = '0' + latPart
  }

  while (lonPart.length < 7) { // adding leading zeros to maintain length
    lonPart = '0' + lonPart
  }

  return latSign + latPart + lonSign + lonPart
}

// Converts id to coordinates
function idToLatLon(id) {
  const latSign = -1 + 2 * parseInt(id.charAt(0)) // character 0
  let latPart = parseInt(id.substring(1, 8)) // character 1 to 7
  const lonSign = -1 + 2 * parseInt(id.charAt(8)) // character 8
  let lonPart = parseInt(id.substring(9, 17)) // character 9 to 16
  return [
    latSign * latPart * TILE_DELTA,
    lonSign * lonPart * TILE_DELTA
  ]
}

async function PopulateTestSquares() {
  const center = [3.7112, 51.0238] //[3.7112, 51.0238] sterre Gent
  for (let i = 0; i < 100; i++) {
    for (let j = 0; j < 100; j++) {
      const _id = latLonToId(center[0] + i * TILE_DELTA, center[1] + j * TILE_DELTA);
      await new SquareModel({
        _id,
        color: Math.floor(Math.random() * 16777215).toString(16)
      }).save()
    }
  }
}

module.exports = {PopulateTestSquares}
