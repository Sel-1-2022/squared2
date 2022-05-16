// const os = require('os');
const mongoose = require('mongoose');

const Square = new mongoose.Schema({
  _id: {
    type: String
  },
  color: {
    type: Number,
    required: true
  },
  island: {
    type: Number,
    required: true
  }
}, { versionKey: false });

module.exports = {SquareModel: mongoose.model('Square', Square)};
