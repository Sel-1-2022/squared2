const {UserModel} = require("../models/UserModel");
module.exports = {
  allusers: async (request, reply) => {
    return UserModel.find();
  },
  postUsers: async (request, reply) => {
    const user = await UserModel.create({
      nickname: request.query.nickname,
      lastLocationUpdate: new Date().getTime(),
      color: request.query.color,
      location: {
        type: "Point",
        coordinates: [
          parseFloat(request.query.latitude),
          parseFloat(request.query.longitude),
        ],
      }
    })
    return (user._id);
  },
  getUsers: async (request, reply) => {
    let user = null;
    if (request.query.id !== undefined) {
      try {
        user = await UserModel.findOne({id: request.query.id});
      } catch (error) {
        console.log(error.message);
        user = null;
      }
    }
    return (user);
  },
  patchUsers: async (request, reply) => {
    let user = null;
    const query = request.query;
    if (query.id !== undefined) {
      try {
        if (query.latitude !== undefined &&
          query.longitude !== undefined) {
          query.lastLocationUpdate = new Date().getTime();
          query.location = {
            type: "Point",
            coordinates: [
              query.latitude,
              query.longitude
            ]
          }
        }
        user = await UserModel.findOneAndUpdate(
          {id: query.id},
          query,
          {new: true}
        );
      } catch (error) {
        console.log(error.message);
        user = null;
      }
    }
    return (user);
  },
  nearbyUsers: async (request, reply) => {
    const users = UserModel.find({
      location: {
        $near: {
          $geometry: {
            type: "Point",
            coordinates: [
              request.query.latitude,
              request.query.longitude
            ]
          },
          $maxDistance: request.query.distance
        }
      }
    });
    return users;
  }
}