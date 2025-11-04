import {Http} from "./http.class.js";
import {SERVER} from "./constants.js";
export class TasksService
{
    #http = new Http();


    getTasks()
    {
        return this.#http.get(SERVER + "/tasks");
    }
    insertTask()
    {
        return this.#http.post(SERVER+"/tasks", "");
    }
}