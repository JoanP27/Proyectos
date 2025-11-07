import {Http} from "./http.class.js";
import {SERVER} from "./constants.js";
export class TasksService
{
    #http = new Http();


    getTasks()
    {
        return this.#http.get(SERVER + "/tasks");
    }
    insertTask(datos)
    {
        console.log("A")
        return this.#http.post(SERVER+"/tasks", datos);
    }
    deleteTask(id)
    {
        return this.#http.delete(SERVER+"/tasks/"+id)
    }
    changeTaskStatus(id, status)
    {
        return this.#http.put(SERVER+"/tasks/"+id, { status: status })

    }
}