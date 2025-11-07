package lectorMP3;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
	public static void main(String[] args)
	{	
		AccesoBBDD basedatos = new AccesoBBDD();
		basedatos.comprobarConexion();		
		try
		{	
			if(args.length < 1) { throw new IllegalArgumentException("Uso: lectorMp3.Main opcion");}
			switch(args[0])
			{
				case "E": 
					if(args.length < 3) { throw new IllegalArgumentException("Uso: lectorMp3.Main E ruta_de_archivo_de_rutas ruta_para_guardar"); }
					escribirFicheros(args[1], args[2]); break;
				case "L": 
					if(args.length < 2) { throw new IllegalArgumentException("Uso: lectorMp3.Main L ruta_archivo_canciones_guardadas"); }
					break;
				case "J":
					if(args.length < 3) { throw new IllegalArgumentException("Uso: lectorMp3.Main L ruta_archivo_canciones_guardadas"); }
					crearJson(args[1], args[2]); break;
				default: System.out.println("Uso: lectorMp3.Main opcion");
			}

			if(args[0].equals("L") && args.length > 1)
			{

				switch(args[1])
				{
					case "C": mostrarCancionesBBDD(); break;
					case "A": mostrarArtistasBBDD(); break;
					case "B": mostrarAlbumesBBDD(); break;
					case "G": mostrarGenerosBBDD(); break;
				}
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	public static void escribirFicheros(String archivoDeRutas, String archivoGuardar) throws IOException, InterruptedException
	{
		ExploradorMP3 exploradorMP3 = new ExploradorMP3(archivoDeRutas);
		exploradorMP3.obtenerDirectorios();
		exploradorMP3.buscarMP3();
		LectorID3 lectorID3 = new LectorID3(exploradorMP3.getResultadoBusqueda());
		lectorID3.comprobarCabeceraID3EnLista();		
		
		ClienteiTunes clienteiTunes = new ClienteiTunes(lectorID3.getListaDeCanciones());
		clienteiTunes.buscarDatosDeLista();
		clienteiTunes.getCanciones().forEach(c -> System.out.println(c));
		exploradorMP3.escribirFicheros(clienteiTunes.getCanciones(), archivoGuardar);
		
		AccesoBBDD basedatos = new AccesoBBDD();
		try {
			basedatos.guardarCancion(clienteiTunes.getCanciones());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void leerFicheros(String archivoDeCanciones) throws ClassNotFoundException, IOException
	{
		List<CancionMP3> canciones = ExploradorMP3.leerMP3(archivoDeCanciones);
		canciones.forEach(c -> System.out.println(c));
	}
	public static void crearJson(String binario, String archivoJson) throws ClassNotFoundException, IOException
	{
		System.out.println("guardando json: ");

		List<CancionMP3> canciones = ExploradorMP3.leerMP3(binario);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String json = gson.toJson(canciones);
		canciones.forEach(c -> System.out.println(c));
		System.out.println(json);

		try (FileWriter writer = new FileWriter(archivoJson + ".json")) {
		    writer.write(json);
		}
		catch (IOException e) {
		    System.err.println("Error al escribir el archivo JSON");
		}
	}
	public static void mostrarCancionesBBDD()
	{
		AccesoBBDD basedatos = new AccesoBBDD();
		try {
			List<CancionMP3> canciones = basedatos.listarCanciones();
			basedatos.listarCanciones().forEach(c ->System.out.println(c));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void mostrarArtistasBBDD()
	{
		AccesoBBDD basedatos = new AccesoBBDD();
		try {
			List<String> canciones = basedatos.listarArtistas();
			canciones.forEach(c -> System.out.println(c));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void mostrarAlbumesBBDD()
	{
		AccesoBBDD basedatos = new AccesoBBDD();
		try {
			List<String> canciones = basedatos.listarAlbumes();
			canciones.forEach(c -> System.out.println(c));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void mostrarGenerosBBDD()
	{
		AccesoBBDD basedatos = new AccesoBBDD();
		try {
			List<String> canciones = basedatos.listarGeneros();
			canciones.forEach(c -> System.out.println(c));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}