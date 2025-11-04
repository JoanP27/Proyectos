const libro = require(__dirname + "/models/libro")
const mongoose = require('mongoose');
const { findByIdAndDelete } = require('./models/libro');

mongoose.connect('mongodb://127.0.0.1:27017/contactos',
{ useNewUrlParser: true, useUnifiedTopology: true });

async function anyadirLibro(titulo, editorial, precio) {
  try {
      let libro1 = new libro({
        titulo: titulo,
        editorial: editorial,
        precio: precio
      });

      const resultado = await libro1.save();
      console.log("Libro añadido:", resultado);
  } catch (error) {
      console.log("ERROR añadiendo contacto:", error);
  }
}
/*
anyadirLibro("El capitán Alatriste", "Alfaguara", 15);
anyadirLibro("El juego de Ender", "Ediciones B", 8.95);
*/
anyadirLibro("El capitán Alatriste", "Alfaguara", 15);
anyadirLibro("El juego de Ender", "Ediciones B", 8.95);

let id = "";
let id2 = "";

libro.find({precio: {$gte: 10, $lte: 20}}).then( resultado => 
{
    id = resultado._id;
}).catch(error => {
    console.log("ERROR:", error)
})

libro.find({nombre: "El juego de Ender"}).then( resultado => 
{
    id2 = resultado;
}).catch(error => {
    console.log("ERROR:", error)
})

console.log("ID: ")

/*


libro.find({nombre: "El juego de Ender"}).then( resultado => 
{
    id2 = resultado._id;
    console.log(resultado);
}).catch(error => {
    console.log("ERROR:", error)
})

libro.findById(
    {id: id}
).then(resultado =>{
console.log(resultado);
}).catch(error => {
    console.log("ERROR:", error)
})

libro.findByIdAndDelete(id).then(
    resultado => {
        console.log(resultado)
    }
).catch()
{
    error =>
    {
        console.log(error)
    }
}

libro.findByIdAndUpdate(id2).then(
    resultado => {
        console.log(resultado)
    }
).catch()
{
    error =>
    {
        console.log(error)
    }
}*/