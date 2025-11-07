import {TasksService} from "./tasks.service.js";

const previewImagen = document.getElementById("image-preview");
const formImagen = document.getElementById("image");
const taskForm = document.getElementById("task-form");
//se ejecuta con el input del campo de imagen, muesrtra la preview de la imagen, y la valida
formImagen.addEventListener("input", function(event)
{
    console.log("input")
    previewImagen.src = "";
    previewImagen.classList.add("hidden")
    let archivo = event.target.files[0];
    let reader = new FileReader();
    if (archivo) reader.readAsDataURL(archivo);
    reader.addEventListener("load", event => {
        previewImagen.src = reader.result;
        previewImagen.classList.remove("hidden");

    }) 

    if(archivo && !archivo.type.startsWith("image"))
    {
        event.target.setCustomValidity("El archivo debe ser una imagen")
    }else if(archivo && archivo.size > 200000) {
        event.target.setCustomValidity("No puedes subir una imagen de mas de 200KB");
    }
    else
    {
        event.target.setCustomValidity("");
    }
})

// comprueba cada vez que un dato cambia de cada campo del formulario las validaciones 
// correspondientes

// la validacion de la imagen no esta aqui ya que sobrescribiria a la validacion hecha
// en el eventListener de arriba

taskForm.addEventListener("input", function(event)
{
    const title = taskForm["title"];
    const description = taskForm["description"];
    const deadLine = taskForm["deadLine"];

    title.setCustomValidity("");
    description.setCustomValidity("");
    deadLine.setCustomValidity("");
    

    if (!title.checkValidity()){
        title.setCustomValidity("El nombre es incorrecto: minimo 5 caracteres, solo letras, numeros y espacios");
    }
    if (!description.checkValidity()){
        description.setCustomValidity("La descripcion es obligatoria");
    }


    const fecha = new Date(deadLine.value);

    if (fecha < Date.now()){
        deadLine.setCustomValidity("La feha no puede ser anterior a la de hoy");
    }
})
// añade la tarea y resetea el formulario
// use async ya que asi esperara a la funcion taskservice de el resultado antes de redirigir al index, sin async la funcion redirige antes de que se haga el post
taskForm.addEventListener("submit", async function(event)
{
    event.preventDefault();

    

    let datosForm = {
        title: taskForm["title"].value,
        address: "",
        filepath: "",
        lat: 1,
        lng: 0,
        description: taskForm["description"].value,
        image: document.getElementById("image-preview").src,
        deadLine: taskForm["deadLine"].value,
        status: 0
    }
    
    const taskService = new TasksService();
    await taskService.insertTask(datosForm);
    taskForm.reset();
    previewImagen.src = "";
    previewImagen.classList.add("hidden")
    location.assign("index.html")


})

//Hecho por: Joan Pomares