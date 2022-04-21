const mongoose = require("mongoose");
const {PopulateTestSquares, latLonToId} = require("./utils/squareUtils");
const {SquareModel} = require("./models/SquareModel");
const {UserModel} = require("./models/UserModel");
const {allUsers, postUsers, getUsers, patchUsers, nearbyUsers} = require("./controllers/UserControllers");
const {nearbySquares, placeSquare} = require("./controllers/SquareController");
const fastify = require('fastify')({logger: true});

// User routes
fastify.get('/api/allusers', allUsers);
fastify.post('/api/user', postUsers);
fastify.get('/api/user', getUsers);
fastify.patch('/api/user', patchUsers);
fastify.get('/api/nearbyusers', nearbyUsers);

// Square routes
fastify.get('/api/nearbysquares', nearbySquares);
fastify.post('/api/placesquare', placeSquare);


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
    await fastify.listen(3000, "127.0.0.1")
    await connectMongo();
    await SquareModel.deleteMany();
    //await PopulateTestSquares();
  } catch (err) {
    fastify.log.error(err)
    process.exit(1)
  }
}

start();
