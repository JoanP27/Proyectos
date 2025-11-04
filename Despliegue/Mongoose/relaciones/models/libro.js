const mongoose = require('mongoose');

let libroSchema = new mongoose.Schema({
    titulo: {
        type: String,
        required: true,
        minlength: 3,
        trim: true
    },
    editorial: {
        type: String,
        unique: true,
        trim: true,
    },
    precio: {
        type: Number,
        min: 0,
        required: true
    }
});

let Libro = mongoose.model("libro", libroSchema);

module.exports = Libro;