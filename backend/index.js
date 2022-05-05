const mongoose = require("mongoose");
const {SquareModel} = require("./models/SquareModel");
const {UserModel} = require("./models/UserModel");
const {allUsers, postUsers, getUsers, patchUsers, nearbyUsers, deleteUsers} = require("./controllers/UserControllers");
const {nearbySquares, placeSquare} = require("./controllers/SquareController");
const path = require("path");
const {PopulateTestSquares, PopulateTestSquaresWithLoop, PopulateTestSquaresWithLoopUnfinished,
  PopulateTestSquaresWithLoopUnfinished3x3
} = require("./utils/testUtils");
const fastify = require('fastify')({logger: true});

fastify.register(require('@fastify/static'), {
  root: path.join(__dirname, 'public'),
})

// User routes
fastify.get('/api/allusers', allUsers);
fastify.post('/api/user', postUsers);
fastify.get('/api/user', getUsers);
fastify.delete('/api/user', deleteUsers);
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
  devUrl: "mongodb://127.0.0.1/",
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
    await PopulateTestSquaresWithLoopUnfinished3x3();
  } catch (err) {
    fastify.log.error(err)
    process.exit(1)
  }
}

start();
