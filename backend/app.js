const mongoose = require("mongoose");
const {SquareModel} = require("./models/SquareModel");
const {UserModel} = require("./models/UserModel");
const {TeamModel} = require("./models/TeamModel");
const {allUsers, postUsers, getUsers, patchUsers, nearbyUsers, deleteUsers, deleteAllUsers, topXUsers} = require("./controllers/UserControllers");
const {nearbySquares, placeSquare} = require("./controllers/SquareController");
const {allTeams, createTeam} = require("./controllers/TeamController");
const path = require("path");
const {PopulateTestSquares, PopulateTestSquaresWithLoop, PopulateTestSquaresWithLoopUnfinished,
  PopulateTestSquaresWithLoopUnfinished3x3, DrawASCII
} = require("./utils/testUtils");
const fastify = require('fastify')({logger: { level: 'error' }});

fastify.register(require('@fastify/static'), {
  root: path.join(__dirname, 'public'),
})

// User routes
fastify.get('/api/allusers', allUsers);
fastify.delete('/api/allusers', deleteAllUsers);
fastify.post('/api/user', postUsers);
fastify.get('/api/user', getUsers);
fastify.delete('/api/user', deleteUsers);
fastify.patch('/api/user', patchUsers);
fastify.get('/api/nearbyusers', nearbyUsers);
fastify.get('/api/topusers', topXUsers);

// Square routes
fastify.get('/api/nearbysquares', nearbySquares);
fastify.post('/api/placesquare', placeSquare);

// Team routes
fastify.get('/api/allteams', allTeams);


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

const initTeams = async () => {
  const numberOfTeams = 12;
  await allTeams().then(
    async (teams) =>  {
      if (teams.length !== 12) {
        console.log("[INIT STATUS]: Incorrect number of teams, resetting teams!");
        await TeamModel.deleteMany().then(
          () => {
            for (let i = 0; i < numberOfTeams; ++i) {
              createTeam(i);
            }
          },
          (error) => {
            console.log("[INIT ERR]: Failed to delete teams!");
            return false; 
          }
        );
      }
      else
      {
        console.log("[INIT STATUS]: Teams already present, no action required!");
      }
    },
    (error) =>
    {
      console.log("[INIT ERR]: Failed to get all teams!");
      return false;
    }
  );
  return true;
}

const start = async () => {
  console.log("test")
  try {
    await fastify.listen(3000, "127.0.0.1")
    await connectMongo();
    await SquareModel.deleteMany();
    await initTeams();
  } catch (err) {
    fastify.log.error(err)
    process.exit(1)
  }
}

module.exports = {start}