const libro = require(__dirname + "/models/libro")
const mongoose = require('mongoose');

mongoose.connect('mongodb://127.0.0.1:27017/contactos',
{ useNewUrlParser: true, useUnifiedTopology: true });


