# Lector de MP3

<h2>Como compilar / Ejecutar:</h2>

Compilar (remplaza user por tu usuario):

```powershell
 javac -cp "/home/user/.m2/repository/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar" \
-d /home/user/eclipse-workspace/lectorMP3/target/classes \
/home/user/eclipse-workspace/lectorMP3/src/main/java/lectorMP3/*.java
```

Ejecutar (remplaza user por tu usuario):

```powershell
java -classpath /home/user/eclipse-workspace/lectorMP3/target/classes:/home/user/.m2/repository/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar lectorMP3.Main
```

Intrucciones de uso:

```powershell
Uso: lectorMp3.Main opcion
```

Opciones:

- E ruta_del_archivo_de_rutas ruta_del_archivo_a_guardar: Lee las direcciones del fichero de rutas y busca ficheros mp3 en ellas, si las encuentra las guardara en la direccion pasada.

```
Ejemplo: java -classpath /home/user/eclipse-workspace/lectorMP3/target/classes:/home/user/.m2/repository/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar lectorMP3.Main E ..\Music\buscar.txt ..\Music\canciones_guardadas
```

- L ruta_del_archivo_de_guardado: Lee los datos del fichero y muestra los MP3 en consola

```
Ejemplo: java -classpath /home/user/eclipse-workspace/lectorMP3/target/classes:/home/user/.m2/repository/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar lectorMP3.Main L ..\Music\canciones_guardadas.bin
```
- J ruta_del_archivo_de_guardado(.bin) archivo_para_guardar_json(no hace falta poner .json)
```
Ejemplo: java -classpath /home/user/eclipse-workspace/lectorMP3/target/classes:/home/user/.m2/repository/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar lectorMP3.Main J ..\Music\canciones_guardadas.bin ..\Music\canciones_guardadas.json
```

<h2>Version de java y dependecias</h2>

Version de java: 21.0.8

Dependencias: Gson 2.10.1

<h3>Decisiones de diseño</h3>

- Creacion de la clase cancionMP3 para guardar la informacion: A la hora de serializar es necesario tener una clase serializable ademas sirve para encapsular la informacion extraida de los mp3

<h2>Limitaciones y trabajo a futuro</h2>

- El lector no es capac de indentificar otras etiquetas aparte de la ID3v1, una mejora a futuro seria hacer que el lectro identifique ID3v2 tambien

- No hay interfaz grafica, otra mejora seria incorporar una

<br>
<p style="text-align: end">author: Joan Pomares Herrero</p>
	
