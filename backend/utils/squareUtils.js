const {SquareModel} = require("../models/SquareModel");

const TILE_DELTA = 0.0001 // 7 dec + sign per lat or lon DONT CHANGE THIS
const TILE_DELTA_INV = 10000

// Round to TILE_DELTA precision
function roundToTileDelta(n){
  return Math.round(n*TILE_DELTA_INV)/TILE_DELTA_INV
}

// Convert coordinates to id
function latLonToId(lat, lon) {
  const latSign = (Math.sign(lat) > 0 ? 1 : 0).toString() // character 0
  let latPart = Math.abs(Math.round(lat / TILE_DELTA)).toString() // character 1 to 7
  const lonSign = (Math.sign(lon) > 0 ? 1 : 0).toString() // character 8
  let lonPart = Math.abs(Math.round(lon / TILE_DELTA)).toString() // character 9 to 15

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
  id = id.toString();
  const latSign = -1 + 2 * parseInt(id.charAt(0)) // character 0
  let latPart = parseInt(id.substring(1, 8)) // character 1 to 7
  const lonSign = -1 + 2 * parseInt(id.charAt(8)) // character 8
  let lonPart = parseInt(id.substring(9, 16)) // character 9 to 15
  return [
    roundToTileDelta(latSign * latPart * TILE_DELTA),
    roundToTileDelta(lonSign * lonPart * TILE_DELTA)
  ]
}

// Bound Longitude to -180 180, if out then it is reduced
function boundLon(lon){
  while(lon < -180){
    lon += 360
  }
  while(lon > 180){
    lon -= 360
  }
  return lon
}

// Bound Latitude to -90 90, if out then it is reduced (This is not continuous on the map)
function boundLat(lat){
  while(lat < -90){
    lat += 180
  }
  while(lat > 90){
    lat -= 180
  }
  return lat
}


// Add lat/lon to an id and get a new id
function addLatLonToId(id, lat, lon){
  const coords = idToLatLon(id);
  coords[0] = boundLat(coords[0] + lat)
  coords[1] = boundLon(coords[1] + lon)
  return latLonToId(coords[0], coords[1])
}

// Creates some test squares
async function PopulateTestSquares() {
  const center = [51.0238, 3.7112] //[51.0238, 3.7112] sterre Gent
  for (let i = 0; i < 100; i++) {
    for (let j = 0; j < 100; j++) {
      const _id = latLonToId(center[0] + i * TILE_DELTA, center[1] + j * TILE_DELTA);
      await new SquareModel({
        _id,
        color: Math.floor(Math.random() * 9)
      }).save()
    }
  }
}

module.exports = {roundToTileDelta, PopulateTestSquares, latLonToId, idToLatLon, boundLon, boundLat, addLatLonToId, TILE_DELTA, TILE_DELTA_INV}
