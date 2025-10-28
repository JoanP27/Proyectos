import express from 'express';
import { fileReader } from './services/fileManager.js';
import { fileURLToPath } from 'url';
import path from 'path';

const app = express();
const PORT = 3000;

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// Servir public/ como raíz (sin /public en la URL)
app.use(express.static(path.join(__dirname, '..', 'public')));

app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, '..', 'public', 'index.html'));
});

app.listen(PORT, () => {
  console.log(`Servidor corriendo en http://localhost:${PORT}`);
});

app.get('/listFiles', (req, res) => {
    const fr = new fileReader();
    let result = fr.listarArchivos();
    
    lista = []

    result.array.forEach(element => {
        lista.append()
    });

    res.status(200).send(fr.listarArchivos());
})