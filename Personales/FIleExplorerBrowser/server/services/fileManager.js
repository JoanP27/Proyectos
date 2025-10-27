import os from "os";
import { readdir } from 'fs/promises';

export class fileReader
{
    #ruta = "./";

    getRuta()
    {
        return this.#ruta;
    }

    listarArchivos = async () => {
        try {
            const archivos = await readdir(this.#ruta);
            console.log(archivos);
        } catch (err) {
            console.error("Error al leer la carpeta:", err);
        }
    };
}


