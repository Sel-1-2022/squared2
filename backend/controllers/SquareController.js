const {invalidQuery} = require("../utils/apiUtils");
const {SquareModel} = require("../models/SquareModel");
const {latLonToId, addLatLonToId, TILE_DELTA, idToLatLon, TILE_DELTA_INV} = require("../utils/squareUtils");
const mongoose = require("mongoose");
module.exports = {
  nearbySquares: async (request, reply) => {
    const {latitude, longitude, distance} = request.query
    if (latitude && longitude && distance) {
      const centerId = latLonToId(latitude, longitude);
      const ids = [];
      for (let i = -distance; i <= distance; i++) {
        for (let j = -distance; j <= distance; j++) {
          let lat = i * TILE_DELTA
          let lon = j * TILE_DELTA
          ids.push(addLatLonToId(centerId, lat, lon))
        }
      }
      const results = await SquareModel.find({
        '_id': {$in: ids}
      });
      return results.map(tile => {
        const coord = idToLatLon(tile._id)
        return {
          color: tile.color,
          lat: coord[0],
          lon: coord[1]
        }
      })
    } else {
      reply.code(400);
      return invalidQuery;
    }
  },
  placeSquare: async (request, reply) => {
    const {latitude, longitude, id, color} = request.query
    if (latitude && longitude && id && color) {
      const lat = Math.floor(latitude*TILE_DELTA_INV)/TILE_DELTA_INV
      const lon = Math.floor(longitude*TILE_DELTA_INV)/TILE_DELTA_INV
      const id = latLonToId(lat, lon);
      console.log(id)
      const squares = await SquareModel.find({_id: id});
      if(squares.length > 0) {
        const square = squares[0]
        square.color = color
        await square.save()
      }else{
        await SquareModel.create({_id: id, color})
      }
      //TODO ADD LAST PLACED TO USER
      return "Succes"
    } else {
      reply.code(400);
      return invalidQuery;
    }
  }

}