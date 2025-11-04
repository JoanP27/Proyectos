package lectorMP3;

import java.io.Serializable;

public class CancionMP3 implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private String ruta = "";
	private String titulo = "";
	private String autor = "";
	private String duracion = "";
	private String album = "";
	private int genero = 0;
	private String anyo = "";
	private String comentario = "";
	private String version = "";
	private String caratula = "";
	private String popularidad= "";
	private String enlaceItunes = "";
	private Float precio;
	public String getRuta()
	{
		return this.ruta;
	}
	
	public void setRuta(String ruta)
	{
		this.ruta = ruta;
	}
	
	public String getTitulo()
	{
		return this.titulo;
	}
	
	public void setTitulo(String titulo)
	{
		this.titulo = titulo;
	}
	
	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getAnyo() {
		return anyo;
	}

	public void setAnyo(String anyo) {
		this.anyo = anyo;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getGenero() {
		return genero;
	}

	public void setGenero(int genero) {
		this.genero = genero;
	}

	public CancionMP3(String ruta, String titulo)
	{
		this.ruta = ruta;
		this.titulo = titulo;
	}

	public String getEnlaceItunes() {
		return enlaceItunes;
	}

	public void setEnlaceItunes(String enlaceItunes) {
		this.enlaceItunes = enlaceItunes;
	}

	public String getPopularidad() {
		return popularidad;
	}

	public void setPopularidad(String popularidad) {
		this.popularidad = popularidad;
	}

	public String getCaratula() {
		return caratula;
	}

	public void setCaratula(String caratula) {
		this.caratula = caratula;
	}

	public Float getPrecio() {
		return precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "CancionMP3 [ruta=" + ruta + ", titulo=" + titulo + ", autor=" + autor
				+ ", duracion=" + duracion + ", album=" + album + ", genero=" + genero + ", anyo=" + anyo
				+ ", comentario=" + comentario + ", version=" + version + ", caratula=" + caratula + ", popularidad="
				+ popularidad + ", enlaceItunes=" + enlaceItunes + ", precio=" + precio + "]";
	}

	
}
