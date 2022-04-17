// Require the framework and instantiate it
const mongoose = require("mongoose");
const {PopulateTestSquares, latLonToId} = require("./utils/squareUtils");
const {SquareModel} = require("./models/SquareModel");
const {UserModel} = require("./models/UserModel");
const fastify = require('fastify')({logger: true});

// Declare a route
fastify.get('/', async (request, reply) => {
  return {
    squared2: '0.0'
  }
})


fastify.get('/user', async (request, reply) => {
  return await UserModel.findOne({id: request.query.id});
})

fastify.post('/user', async (request, reply) => {
  return {
    status: 1
  }
})

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

// Run the server!
const start = async () => {
  try {
    await fastify.listen(3000)
    await connectMongo();
    await SquareModel.deleteMany(); // this clears the DB
    await PopulateTestSquares();
  } catch (err) {
    fastify.log.error(err)
    process.exit(1)
  }
}
start();
