// Require the framework and instantiate it
const mongoose = require("mongoose");
const {PopulateTestSquares} = require("./utils/squareUtils");
const {SquareModel} = require("./models/SquareModel");
const {UserModel} = require("./models/UserModel");
const fastify = require('fastify')({logger: true});

// Declare a route
fastify.get('/', async (request, reply) => {
  return {hello: 'world'}
})


fastify.get('/user_get', async (request, reply) => {
  return {params: request.params, 
          body: request.body, 
          query: request.query, 
          header: request.header}
})

fastify.post('/user_update', async (request, reply) => {
  // TODO (Elias)
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
