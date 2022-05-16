const {UserModel} = require("../models/UserModel");
const {formatError, invalidQuery} = require("../utils/apiUtils");
module.exports = {
  allUsers: async (request, reply) => {
    return UserModel.find();
  },
  postUsers: async (request, reply) => {
    let user;
    if(request.query.lastLocationUpdate){
      reply.code(400);
      return invalidQuery;
    }
    try {
      user = await UserModel.create({
        nickname: request.query.nickname,
        lastLocationUpdate: new Date().getTime(),
        lastSquareColored: -1,
        color: request.query.color,
        location: {
          type: "Point",
          coordinates: [
            parseFloat(request.query.longitude),
            parseFloat(request.query.latitude),
          ],
        },
        squaresCaptured: 0
      })
    } catch (err) {
      reply.code(400);
      return formatError(err.message);
    }
    return user._id;
  },
  getUsers: async (request, reply) => {
    if (request.query.id !== undefined) {
      return await UserModel.findById(request.query.id).catch(err => {
        reply.code(400);
        return formatError(err.message);
      });
    }
    reply.code(400);
    return invalidQuery;
  },
  deleteUsers: async (request, reply) => {
    if (request.query.id !== undefined) {
      return await UserModel.findByIdAndDelete(request.query.id).then(
        (data) => {
          return 0;
        },
        (error) => {
          reply.code(400);
          return formatError(err.message);
        });
    }
    reply.code(400);
    return invalidQuery;
  },
  deleteAllUsers: async (request, reply) => {
      return await UserModel.remove({});
  },
  patchUsers: async (request, reply) => {
    const query = request.query;
    if(query.lastLocationUpdate){
      reply.code(400);
      return invalidQuery;
    }
    if (query.id !== undefined) {
      try {
        if (query.longitude !== undefined &&
          query.latitude !== undefined) {
          query.lastLocationUpdate = new Date().getTime();
          query.location = {
            type: "Point",
            coordinates: [
              query.longitude,
              query.latitude
            ]
          }
        }
        const user = await UserModel.findOneAndUpdate(
          {_id: query.id},
          query,
          {new: true}
        );
        if(user){
          return user;
        }
        else {
          reply.code(400);
          return invalidQuery;
        }
      } catch (error) {
        reply.code(400);
        return formatError(error.message);
      }
    }
    reply.code(400);
    return invalidQuery;
  },
  nearbyUsers: async (request, reply) => {
    let currentTime = new Date().getTime();
    const threshold = 30000;
    return UserModel.find({
      location: {
        $near: {
          $geometry: {
            type: "Point",
            coordinates: [
              request.query.longitude,
              request.query.latitude
            ]
          },
          $maxDistance: request.query.distance
        }
      },
    }).then(
      (data) => {
        return data.filter((element) => ((currentTime - element.lastLocationUpdate) < threshold));
      },
      (error) => {
        reply.code(400);
        return formatError(err.message);
      }
    );
  },
  topXUsers: async (request, reply) => {
    if (request.query.amount !== undefined) {
      return UserModel.find({}).sort({score : -1}).limit(request.query.amount).catch(err => {
        reply.code(400);
        return formatError(err.message);
      });
    }
    reply.code(400);
    return invalidQuery;
  }
}





















