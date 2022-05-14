// const os = require('os');
const mongoose = require('mongoose');

const Team = new mongoose.Schema(
  {
    color: {
      type: Number,
      unique: true,
      required: true
    },
    squaresCaptured:
    {
      type: Number,
      required: true
    }
  }, 
  {versionKey: false}
);

module.exports = {TeamModel: mongoose.model('Team', Team)};
