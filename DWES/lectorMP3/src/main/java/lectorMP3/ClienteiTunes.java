package lectorMP3;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class ClienteiTunes {	
	private List<CancionMP3> canciones;
	
	
	
	public ClienteiTunes(List<CancionMP3> canciones) {
		this.canciones = canciones;
	}
	public List<CancionMP3> getCanciones() {
		return canciones;
	}
	public void setCanciones(List<CancionMP3> canciones) {
		this.canciones = canciones;
	}
	public String construirEnlace(CancionMP3 cancion)
	{
		String term = cancion.getTitulo() + " " + cancion.getAutor();
		String url = "https://itunes.apple.com/search?entity=song&limit=1&term=" + URLEncoder.encode(term, StandardCharsets.UTF_8);
		cancion.setEnlaceItunes(url);
		return url;
	}
	public void buscarDatosDeLista()
	{
		for (CancionMP3 c : canciones)
		{
			try {
				obtenerDatos(c);
			} catch (IOException | InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	public String obtenerDatos(CancionMP3 cancion) throws IOException, InterruptedException
	{
		if(cancion.getEnlaceItunes().isEmpty()) { construirEnlace(cancion); }
		
		HttpClient client = HttpClient.newBuilder()
				 .version(HttpClient.Version.HTTP_2)
				 .connectTimeout(Duration.ofSeconds(5))
				 .build();
		
		HttpRequest request = HttpRequest.newBuilder()
				 .uri(URI.create(cancion.getEnlaceItunes()))
				 .timeout(Duration.ofSeconds(10))
				 .header("Accept", "application/json")
				 .GET()
				 .build();
		
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		int status = response.statusCode();
		if (status != 200) { throw new IOException("HTTP " + status + " -> " + response.body()); }
		
		Gson gson = new Gson();
		JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
		
		JsonArray results = json.getAsJsonArray("results");
		
		JsonObject firstResult = results.get(0).getAsJsonObject();
	    String artworkUrl = firstResult.get("artworkUrl100").getAsString();
		Float precio = firstResult.get("trackPrice").getAsFloat();
		HttpRequest imgReq = HttpRequest.newBuilder(URI.create(artworkUrl))
		    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0 Safari/537.36")
		    .GET()
		    .build();

		HttpResponse<Path> r = client.send(imgReq, HttpResponse.BodyHandlers.ofFile(Paths.get(cancion.getTitulo()+"_caratula.jpg")));
		
		cancion.setPrecio(precio);
		if (r.statusCode() == 200) {
		    System.out.println("Caratula ha sido guardada en: " + r.body());
		    cancion.setCaratula(artworkUrl);
		    
		} 
		
		List<String> datosLog = new ArrayList<String>();
		
		LocalDateTime fecha = LocalDateTime.now();
		DateTimeFormatter fechaFormat = DateTimeFormatter.ofPattern("dd/mm/yyyy - HH:mm:ss"); 
		
		
		escribirLog(fechaFormat.format(fecha) + ";" + cancion.getEnlaceItunes() + ";" + status);
	    

		return json.toString();
	}
	public void escribirLog(String datos)
	{

		try (PrintWriter printWriter = new PrintWriter (new BufferedWriter(
				new FileWriter("iTunesLog.txt", true))))
        {
            printWriter.println(datos);
        }
        catch (IOException e) 
        {
            System.err.println("Error writing file: " + e.getMessage());
        }
		
	}
}
