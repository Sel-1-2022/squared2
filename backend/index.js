const mongoose = require("mongoose");
const {PopulateTestSquares, latLonToId} = require("./utils/squareUtils");
const {SquareModel} = require("./models/SquareModel");
const {UserModel} = require("./models/UserModel");
const {allusers, postUsers, getUsers, patchUsers, nearbyUsers} = require("./controllers/UserControllers");
const fastify = require('fastify')({logger: true});

// User routes
fastify.get('/allusers', allusers);
fastify.post('/user', postUsers);
fastify.get('/user', getUsers);
fastify.patch('/user', patchUsers);
fastify.get('/nearbyusers', nearbyUsers);

// Square routes


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
