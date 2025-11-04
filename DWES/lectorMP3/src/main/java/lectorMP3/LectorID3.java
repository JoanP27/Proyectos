package lectorMP3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LectorID3 {
	private List<CancionMP3> listaDeCanciones = new ArrayList<CancionMP3>();
	private List<File> archivosComprobar = new ArrayList<File>();
	
	
	
	
	
	public LectorID3(List<File> archivosComprobar) {
		this.archivosComprobar = archivosComprobar;
	}

	public List<CancionMP3> getListaDeCanciones() {
		return listaDeCanciones;
	}
	
	public List<File> getArchivosComprobar() {
		return archivosComprobar;
	}

	public void setArchivosComprobar(List<File> archivosComprobar) {
		this.archivosComprobar = archivosComprobar;
	}

	public void comprobarCabeceraID3EnLista() throws IOException
	{
		for (File archivo : archivosComprobar)
		{
			comprobarCabeceraID3(archivo);
		}
	}
	
	public void comprobarCabeceraID3(File archivo) throws IOException
	{
		CancionMP3 nueva_cancion = null;
		
		if (!archivo.getName().contains(".mp3")) {
			 throw new IllegalArgumentException("Uso: java LectorID3v1RAF <archivo.mp3>");	 
		}
		
		try(RandomAccessFile raf = new RandomAccessFile(archivo, "r")){
			
			if(raf.length() < 128) {
				throw new IllegalArgumentException("El archivo es muy pequeño");
			}
			raf.seek(raf.length() - 128);
			
			byte[] buffer = new byte[128];
			
			raf.readFully(buffer);
			
			String tag = new String(buffer, 0, 3, StandardCharsets.ISO_8859_1);
			if (!"TAG".equals(tag)) {
				throw new IllegalArgumentException("el archivo "+ archivo.getName() +" no tiene cabecera ID3v1");
			}
			
			String titulo = new String(buffer, 3, 30, StandardCharsets.ISO_8859_1).trim();
			String autor = new String(buffer, 33, 30, StandardCharsets.ISO_8859_1).trim();
			String album = new String(buffer, 63, 30, StandardCharsets.ISO_8859_1).trim();
			String anyo = new String(buffer, 93, 4, StandardCharsets.ISO_8859_1).trim();
			String comentario = new String(buffer, 97, 30, StandardCharsets.ISO_8859_1).trim();
			int genero = buffer[127] & 0xFF; 
			
			nueva_cancion = new CancionMP3(archivo.getAbsolutePath(), titulo);
			nueva_cancion.setAutor(autor);
			nueva_cancion.setAlbum(album);
			nueva_cancion.setAnyo(anyo);
			nueva_cancion.setComentario(comentario);
			nueva_cancion.setGenero(genero);
			
			listaDeCanciones.add(nueva_cancion);
			
			
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
	}

}
