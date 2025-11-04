package lectorMP3;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ExploradorMP3 {
	private String rutaBusqueda;
	private List<File> resultadoBusqueda = new ArrayList<File>();
	private List<File> directoriosConMP3 = new ArrayList<File>();
	public String getRutaBusqueda() {
		return rutaBusqueda;
	}
	public void setRutaBusqueda(String ruta_busqueda) {
		this.rutaBusqueda = ruta_busqueda;
	}
	public List<File> getResultadoBusqueda() {
		return resultadoBusqueda;
	}
	public void setResultadoBusqueda(List<File> resultadoBusqueda) {
		this.resultadoBusqueda = resultadoBusqueda;
	}
	
	public ExploradorMP3(String ruta_busqueda) {
		this.rutaBusqueda = ruta_busqueda;
	}
	
	public void buscarMP3()
	{
		for (File d : directoriosConMP3)
		{
			resultadoBusqueda.addAll(buscarEnDirectorio(d.getAbsolutePath()));
		}
	}
	public List<File> obtenerDirectorios() throws IOException
	{
		
		File fichero = new File(rutaBusqueda);
		
		if(!fichero.exists())
		{
			throw new IllegalArgumentException("El fichero no se puedo encontrar");
		}
		if (fichero.isDirectory()) {
			throw new IllegalArgumentException("La ruta no es un archivo");
		}		
		
		List<String> rutas = Files.readAllLines(Paths.get(fichero.getAbsolutePath()));
		for (String rutaBuscada : rutas)
		{
			File archivoBuscado = new File(rutaBuscada);
			if(archivoBuscado.exists() && archivoBuscado.isDirectory())
			{
				directoriosConMP3.add(archivoBuscado);
			}
		}
		return directoriosConMP3;
	}
	public static  ArrayList<File> buscarEnDirectorio(String ruta)
	{
		File folder = new File(ruta);

		if(!folder.exists())
		{
			throw new IllegalArgumentException("El fichero no se puedo encontrar");
		}
		if (!folder.isDirectory()) {
			throw new IllegalArgumentException("La ruta no es un directorio");
		}
		
		String[] listaDirecciones = folder.list(); 
		
		if(listaDirecciones == null) {
			return new ArrayList<File>();
		}
		ArrayList<File> listaMP3 = new ArrayList<File>();
		for (String rutaHijo : listaDirecciones) {
			File ficheroHijo = new File(folder, rutaHijo);
			if(ficheroHijo.isDirectory()){
				listaMP3.addAll(buscarEnDirectorio(ficheroHijo.getAbsolutePath())); 
			}
			else if(rutaHijo.contains(".mp3")){
				listaMP3.add(ficheroHijo);
			}
		}
		return listaMP3;
	}
	public void escribirFicheros(List<CancionMP3> canciones, String ruta_guardar) throws IOException
	{	
		try(FileOutputStream fos = new FileOutputStream(ruta_guardar + ".bin");
				ObjectOutputStream oos = new ObjectOutputStream(fos))
		{
	
			oos.writeObject(canciones);
		}
		catch(IOException e)
		{
			System.err.println("Error al escribir el archivo");
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		
	}
	@SuppressWarnings("unchecked")
	public static ArrayList<CancionMP3> leerMP3(String ruta) throws IOException, ClassNotFoundException
	{
		if(Files.isDirectory(Paths.get(ruta).toAbsolutePath()) || !
				Files.exists(Paths.get(ruta).toAbsolutePath())) {throw new IllegalArgumentException("La ruta no es de un fichero");}
		
		ArrayList<CancionMP3> canciones = new ArrayList<CancionMP3>();
		
		try (FileInputStream fis = new FileInputStream(ruta);
				ObjectInputStream ois = new ObjectInputStream(fis)) {
			
				canciones.addAll((ArrayList<CancionMP3>) ois.readObject());
		} 
		
		return canciones;
	}
	
	
	
}
