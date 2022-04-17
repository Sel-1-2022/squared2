// const os = require('os');
const mongoose = require('mongoose');

const Square = new mongoose.Schema({
  _id: {
    type: Number,
    required: true,
    index: true,
    unique: true
  },
  color: {
    type: String,
    required: true
  }
}, { versionKey: false });

module.exports = {SquareModel: mongoose.model('Square', Square)};
