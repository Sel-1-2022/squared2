const {TeamModel} = require("../models/TeamModel");
const {formatError, invalidQuery} = require("../utils/apiUtils");

module.exports = {
  createTeam: async (color) => 
  {
    let team;
    try {
      team = await TeamModel.create({
        color: color,
        squaresCaptured: 0
      })
    } catch (err) {
      reply.code(400);
      return formatError(err.message);
    }
  },
  allTeams: async (request, reply) => {
    return TeamModel.find();
  },
}
