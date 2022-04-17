// const os = require('os');
const mongoose = require('mongoose');

const pointSchema = new mongoose.Schema({
  type: {
    type: String,
    enum: ['Point'],
    required: true
  },
  coordinates: {
    type: [Number],
    required: true
  }
});

const User = new mongoose.Schema({
  _id: {
    type: String,
    required: true,
    index: true,
    unique: true
  },
  nickname: {
    type: String,
    required: true
  },
  lastUpdate: { // epoch date of last update
    type: Number,
    required: true
  },
  color: {
    type: String,
    required: true
  },
  location: {
    type: pointSchema,
    index: '2dsphere' // Create a special 2dsphere index
  }
}, {versionKey: false});

module.exports = {UserModel: mongoose.model('User', User)};
