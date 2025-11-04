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
					leerFicheros(args[1]); break;
				case "J":
					if(args.length < 3) { throw new IllegalArgumentException("Uso: lectorMp3.Main L ruta_archivo_canciones_guardadas"); }
					crearJson(args[1], args[2]); break;
				default: System.out.println("Uso: lectorMp3.Main opcion");
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
			basedatos.gurdarCancion(clienteiTunes.getCanciones().get(0));
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
	
}