import {TasksService} from "./tasks.service.js";
const taskTemplate = document.getElementById("task-template")

const taskService = new TasksService();

const resultado = await taskService.getTasks();


for (let i of resultado.properties)
{
    crearTarea(i)
}

// crea la tarea apartir de los datos en el eventListener anterior
function cambiarTarea(valor, tarjeta, datos)
{
    console.log(tarjeta, valor)

    const pendiente = document.getElementById("pending-tasks");
    const enProceso = document.getElementById("in-progress-tasks");
    const terminada = document.getElementById("completed-tasks");

    console.log()

    switch(valor)
    {
        case '0': pendiente.append(tarjeta); taskService.changeTaskStatus(datos.id,valor); break;
        case '1': enProceso.append(tarjeta); taskService.changeTaskStatus(datos.id,valor)
        break;
        case '2': terminada.append(tarjeta); taskService.changeTaskStatus(datos.id,valor); break;
    }
}
// cambia la tarea de contenedor
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
    select.addEventListener("change", (event) => cambiarTarea(event.target.value, contenido, datos))
    eliminar.addEventListener("click", (event) => eliminarTarea(contenido, datos))

}

function eliminarTarea(tarea, datosTarea)
{
    const resultadoEliminar = taskService.deleteTask(datosTarea.id)
    tarea.remove()
}

//Hecho por: Joan Pomares