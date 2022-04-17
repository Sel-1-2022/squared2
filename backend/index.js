// Require the framework and instantiate it
const mongoose = require("mongoose");
const {PopulateTestSquares, latLonToId} = require("./utils/squareUtils");
const {SquareModel} = require("./models/SquareModel");
const {UserModel} = require("./models/UserModel");
const fastify = require('fastify')({logger: true});

fastify.post('/user', async (request, reply) => {
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
  return user._id;
})

fastify.get('/user', async (request, reply) => {
  let user = null;
  if (request.query.id !== undefined)
  {
    try {
      user = await UserModel.findOne({id: request.query.id});
    } catch (error) {
      console.log(error.message);
      user = null;
    }
  }
  return user;
})

fastify.patch('/user', async (request, reply) => {
  let user = null;
  let query = request.query;
  if (query.id !== undefined)
  {
    try {
      if (query.latitude !== undefined &&
          query.longitude !== undefined)
      {
        query.lastLocationUpdate = new Date().getTime();
        // TODO (Elias): Find way to update location
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
  return user 
})

/*----------------------------*/

const mongoOptions = {
  options: {
    useNewUrlParser: true,
    keepAlive: true,
    useUnifiedTopology: true
  },
  databaseName: "squared2",
  devUrl: "mongodb://localhost/",
}

function connectMongo() {
  return mongoose.connect(mongoOptions.devUrl + mongoOptions.databaseName, mongoOptions.options, (err) => {
    if (err) {
      console.log(err);
      setTimeout(() => {
        connectMongo();
      }, 4000);
      console.error('Failed to connect to db retrying', err);
    } else {
      console.log('connected to mongo')
    }
  });
}

const start = async () => {
  try {
    await fastify.listen(3000)
    await connectMongo();
    await SquareModel.deleteMany();
    await PopulateTestSquares();
  } catch (err) {
    fastify.log.error(err)
    process.exit(1)
  }
}

start();
