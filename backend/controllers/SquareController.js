const {invalidQuery} = require("../utils/apiUtils");
const {SquareModel} = require("../models/SquareModel");
const {lonLatToId, addLonLatToId, TILE_DELTA, idToLonLat, TILE_DELTA_INV} = require("../utils/squareUtils");
const mongoose = require("mongoose");
const {getIslandColorAndJoinLoops} = require("../utils/islandUtils");

module.exports = {
  nearbySquares: async (request, reply) => {
    const {longitude, latitude,  distance} = request.query
    if (longitude && latitude && distance) {
      const centerId = lonLatToId(longitude, latitude);
      const ids = [];
      for (let i = -distance; i <= distance; i++) {
        for (let j = -distance; j <= distance; j++) {
          let lon = i * TILE_DELTA
          let lat = j * TILE_DELTA
          ids.push(addLonLatToId(centerId, lon, lat))
        }
      }
      const results = await SquareModel.find({
        '_id': {$in: ids}
      });
      return results.map(tile => {
        const coord = idToLonLat(tile._id)
        return {
          color: tile.color,
          lon: coord[0],
          lat: coord[1],
          island: tile.island,
          id: tile._id, // For debug
        }
      })
    } else {
      reply.code(400);
      return invalidQuery;
    }
  },
  placeSquare: async (request, reply) => {
    let {longitude, latitude, id, color} = request.query
    if (longitude && latitude && id && color) {
      color = parseInt(color)
      longitude = parseFloat(longitude)
      latitude = parseFloat(latitude)
      const id = lonLatToId(longitude, latitude);
      const squares = await SquareModel.find({_id: id});
      let square;
      if(squares.length > 0) {
        square = squares[0]
        square.color = color
        square.island = await getIslandColorAndJoinLoops(color, longitude, latitude)
        await square.save()
      }else{
        square = await SquareModel.create({
          _id: id,
          color,
          island: await getIslandColorAndJoinLoops(color, longitude, latitude)
        })
      }
      //TODO ADD LAST PLACED TO USER
      return square
    } else {
      reply.code(400);
      return invalidQuery;
    }
  }

}
