const previewImagen = document.getElementById("image-preview");
const formImagen = document.getElementById("image");
const taskForm = document.getElementById("task-form");
const taskTemplate = document.getElementById("task-template")
//se ejecuta con el input del campo de imagen, muesrtra la preview de la imagen, y la valida
formImagen.addEventListener("input", function(event)
{
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
taskForm.addEventListener("submit", function(event)
{
    event.preventDefault();


    let datosForm = {
        title: taskForm["title"].value,
        description: taskForm["description"].value,
        image: document.getElementById("image-preview").src,
        deadLine: taskForm["deadLine"].value,
        status: 0
    }
    crearTarea(datosForm);
    taskForm.reset();
    previewImagen.src = "";
    previewImagen.classList.add("hidden")
})

// crea la tarea apartir de los datos en el eventListener anterior
function crearTarea(datos)
{
    const tareaNueva = taskTemplate.content.cloneNode(true);
    const contenido = tareaNueva.firstElementChild


    contenido.children[0].textContent = datos.title;
    contenido.children[1].textContent = datos.description;
    contenido.children[2].src = datos.image;
    if(contenido.children[2].src.startsWith("data:image")) 
    {
        contenido.children[2].classList.toggle("hidden") 

    }


    const date = new Date(datos.deadLine);
    contenido.children[3].textContent = new Intl.DateTimeFormat('es-ES', {
        day: "2-digit", month: "2-digit", year: "numeric"
    }).format(date);

    document.getElementById("pending-tasks").append(contenido);


    const select = contenido.children[4].firstElementChild
    const eliminar = contenido.children[4].lastElementChild
    select.addEventListener("change", (event) => cambiarTarea(event.target.value, contenido))
    eliminar.addEventListener("click", (event) => contenido.remove())

}
// cambia la tarea de contenedor
function cambiarTarea(valor, tarjeta)
{
    console.log(tarjeta, valor)

    const pendiente = document.getElementById("pending-tasks");
    const enProceso = document.getElementById("in-progress-tasks");
    const terminada = document.getElementById("completed-tasks");

    console.log()

    switch(valor)
    {
        case '0': pendiente.append(tarjeta); break;;
        case '1': enProceso.append(tarjeta); break;
        case '2': terminada.append(tarjeta); break;
    }
}
//Hecho por: Joan Pomares