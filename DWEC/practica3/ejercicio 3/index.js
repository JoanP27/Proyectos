// Completa este archivo


const previewImagen = document.getElementById("image-preview");
const formImagen = document.getElementById("image")
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
    if(!archivo.type.startsWith("image"))
    {
        event.target.setCustomValidity("El archivo debe ser una imagen")
    }else if(archivo.size > 200000) {
        event.target.setCustomValidity("No puedes subir una imagen de mas de 200KB");
    }
    else
    {
        event.target.setCustomValidity("");
    }
})

//TODO Usar API contraint validation

const taskForm = document.getElementById("task-form");
const taskTemplate = document.getElementById("task-template")
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
    crearTarea(datosForm)
})


function crearTarea(datos)
{
    const tareaNueva = taskTemplate.content.cloneNode(true);
    const contenido = tareaNueva.firstElementChild

    contenido.children[0].textContent = datos.title;
    contenido.children[1].textContent = datos.description;
    contenido.children[2].src = datos.image;
    contenido.children[3].textContent = datos.deadLine;

    contenido.children[2].classList.toggle("hidden") 



    document.getElementById("pending-tasks").append(contenido);


    const select = contenido.children[4].firstElementChild
    const eliminar = contenido.children[4].lastElementChild
    select.addEventListener("change", (event) => cambiarTarea(event.target.value, contenido))
    eliminar.addEventListener("click", (event) => contenido.remove())

}
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