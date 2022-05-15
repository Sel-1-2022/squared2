const {invalidQuery} = require("../utils/apiUtils");
const {SquareModel} = require("../models/SquareModel");
const {UserModel} = require("../models/UserModel");
const {TeamModel} = require("../models/TeamModel");
const {lonLatToId, addLonLatToId, TILE_DELTA, idToLonLat, TILE_DELTA_INV} = require("../utils/squareUtils");
const mongoose = require("mongoose");
const {getIslandColorAndJoinLoops} = require("../utils/islandUtils");

function doLooping(color, longitude, latitude, id){
  return getIslandColorAndJoinLoops(color, longitude, latitude).then(async island => { // Dont await this so the request can return while the looping calculation runs
    await SquareModel.updateOne({_id: id}, {island})
  })
}

module.exports = {
  nearbySquares: async (request, reply) => {
    const {longitude, latitude, distance} = request.query
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
    let placed = false; // Is an actual square placed?
    let {longitude, latitude, id, color} = request.query
    if (longitude && latitude && id && color) {
      color = parseInt(color)
      longitude = parseFloat(longitude)
      latitude = parseFloat(latitude)
      const id = lonLatToId(longitude, latitude);
      let square = await SquareModel.findOne({_id: id});
      if (square) {
        console.log(square)
        if (square.color !== color) {
          square = squares[0]
          await TeamModel.findOneAndUpdate({color: square.color}, {$inc: {squaresCaptured: -1}});
          square.color = color
          square.island = -1
          await square.save()
          placed = true;
        }
      } else {
        square = await SquareModel.create({
          _id: id,
          color,
          island: -1
        });
        placed = true;
      }
      if(placed){
        await UserModel.findByIdAndUpdate(request.query.id, {$inc: {squaresCaptured: 1}});
        await TeamModel.findOneAndUpdate({color: color}, {$inc: {squaresCaptured: 1}});
        doLooping(color, longitude, latitude, id);
      }
      return square
    } else {
      reply.code(400);
      return invalidQuery;
    }
  }

}
