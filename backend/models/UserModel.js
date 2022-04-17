// const os = require('os');
const mongoose = require('mongoose');

const User = new mongoose.Schema({
  _id: {
    type: String,
    required: true,
    index: true,
    unique: true
  },
  lastUpdate: { //epoch date of last update
    type: Number,
    required: true
  },
  color: {
    type: String,
    required: true
  }
}, { versionKey: false });

module.exports = {UserModel: mongoose.model('User', User)};
