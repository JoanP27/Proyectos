package lectorMP3;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccesoBBDD {
	
	private String url;
	private String passwd;
	private String usuario;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public AccesoBBDD(String url,String usuario, String passwd)
	{
		this.setUrl(url);
		this.setPasswd(passwd);
		this.setUsuario(usuario);
	}
	public AccesoBBDD()
	{
		this.setUrl("jdbc:postgresql://localhost:5432/music_library");
		this.setPasswd("1234");
		this.setUsuario("postgres");
	}
	public int comprobarConexion()
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection(this.url, this.usuario, this.passwd);
			System.out.println("BBDD: 🟢 Conexión correcta!");
			con.close();
			return 0;
		}catch(Exception e)
		{
			System.err.println("BBDD: ❌ " + e.getMessage());
			return -1;
		}
		
	}
	public List<CancionMP3> listarCanciones() throws ClassNotFoundException, SQLException
	{
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(this.url, this.usuario, this.passwd);
		
		PreparedStatement ps=con.prepareStatement("SELECT t.id, t.file_path, t.title, t.genre_id, t.itunes_url, t.itunes_artwork_url, t.file_name,"
				+ "al.title, ar.name, al.year"
				+ " FROM track t"
				+ " JOIN album al ON t.album_id = al.id"
				+ " JOIN artist ar ON al.artist_id = ar.id");
		
		ResultSet rs=ps.executeQuery();
		
		List<CancionMP3> canciones = new ArrayList<CancionMP3>();
		
		int idCancion = -1;
		
		while(rs.next()){
			CancionMP3 cancion = new CancionMP3(rs.getString(2), rs.getString(3));
			cancion.setGenero(rs.getInt(4));
			cancion.setEnlaceItunes(rs.getString(5));
			cancion.setCaratula(rs.getString(6));
			cancion.setNombreArchivo(rs.getString(7));
			cancion.setAlbum(rs.getString(8));
			cancion.setAutor(rs.getString(9));
			cancion.setAnyo(rs.getString(10));

			canciones.add(cancion);
		}
		
		ps.close();
		con.close();
		return canciones;	
	}
	public List<String> listarGeneros() throws ClassNotFoundException, SQLException
	{
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(this.url, this.usuario, this.passwd);
		
		PreparedStatement ps=con.prepareStatement("select * from genre");

		List<String> generos = new ArrayList<String>();
		
		ResultSet rs=  ps.executeQuery();
		
		while(rs.next()){
			generos.add(rs.getString(2));
		}
		ps.close();
		con.close();
		return generos;
	}
	public List<String> listarAlbumes() throws ClassNotFoundException, SQLException
	{
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(this.url, this.usuario, this.passwd);
		
		PreparedStatement ps=con.prepareStatement("select * from album");

		List<String> albumes = new ArrayList<String>();
		
		ResultSet rs=  ps.executeQuery();
		
		while(rs.next()){
			albumes.add(rs.getString(2));
		}
		ps.close();
		con.close();
		return albumes;
	}
	public List<String> listarArtistas() throws ClassNotFoundException, SQLException
	{
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(this.url, this.usuario, this.passwd);
		
		PreparedStatement ps=con.prepareStatement("select * from artist");

		List<String> artistas = new ArrayList<String>();
		
		ResultSet rs=  ps.executeQuery();
		
		while(rs.next()){
			artistas.add(rs.getString(2));
		}
		ps.close();
		con.close();
		return artistas;
	}
	public CancionMP3 buscarCancion(String nombre) throws ClassNotFoundException, SQLException
	{
		
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(this.url, this.usuario, this.passwd);
		
		PreparedStatement ps=con.prepareStatement("SELECT t.id, t.file_path, t.title, t.genre_id, t.itunes_url, t.itunes_artwork_url, t.file_name,"
				+ "al.title, ar.name, al.year"
				+ " FROM track t"
				+ " JOIN album al ON t.album_id = al.id"
				+ " JOIN artist ar ON al.artist_id = ar.id"
				+ " WHERE t.title = ?;");
		
		ps.setString(1, nombre);
		
		ResultSet rs=ps.executeQuery();
		
		CancionMP3 cancion = null;
				
		while(rs.next()){
			cancion = new CancionMP3(rs.getString(2), rs.getString(3));
			cancion.setGenero(rs.getInt(4));
			cancion.setEnlaceItunes(rs.getString(5));
			cancion.setCaratula(rs.getString(6));
			cancion.setNombreArchivo(rs.getString(7));
			cancion.setAlbum(rs.getString(8));
			cancion.setAutor(rs.getString(9));
			cancion.setAnyo(rs.getString(10));
		}
		
		ps.close();
		con.close();
		return cancion;
	}
	public int buscarArtista(String nombre) throws ClassNotFoundException, SQLException
	{
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(this.url, this.usuario, this.passwd);
		
		PreparedStatement ps=con.prepareStatement("select * from artist where name = ?");

		ps.setString(1, nombre);
		
		ResultSet rs=  ps.executeQuery();
		int result = -1;
		while(rs.next()){
			result = rs.getInt(1);
		}
		ps.close();
		con.close();
		return result;
	}
	public int buscarGenero(String nombre) throws ClassNotFoundException, SQLException
	{
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(this.url, this.usuario, this.passwd);
		
		PreparedStatement ps=con.prepareStatement("select * from genre where name = ?");

		ps.setString(1, nombre);
		
		ResultSet rs=  ps.executeQuery();
		int result = -1;
		while(rs.next()){
			result = rs.getInt(1);
		}
		ps.close();
		con.close();
		return result;
	}
	public int buscarAlbum(String titulo) throws ClassNotFoundException, SQLException
	{
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(this.url, this.usuario, this.passwd);
		
		PreparedStatement ps=con.prepareStatement("select * from album where title = ?");

		ps.setString(1, titulo);
		
		ResultSet rs=  ps.executeQuery();
		int result = -1;
		while(rs.next()){
			result = rs.getInt(1);
		}
		ps.close();
		con.close();
		return result;
	}
	public void guardarArtista(String nombre) throws ClassNotFoundException, SQLException
	{
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(this.url, this.usuario, this.passwd);
		
		PreparedStatement ps = con.prepareStatement("INSERT INTO artist(name) VALUES(?)");
		
		ps.setString(1, nombre);
		ps.execute();
		
		ps.close();
		con.close();
	}
	public void guardarAlbum(String titulo, String fecha, Integer artistId) throws ClassNotFoundException, SQLException
	{
		if(titulo.isEmpty()) {
			titulo = "sin nombre"; // No se ha aportado ningun album, asi que solo se guardara el artista
		}
		
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(this.url, this.usuario, this.passwd);
		
		PreparedStatement ps = con.prepareStatement("INSERT INTO album(title, year, artist_id) VALUES(?,?,?)");
		
		ps.setString(1, titulo);
		ps.setInt(2, Integer.parseInt(fecha));
		ps.setInt(3, artistId);

		ps.execute();
		
		ps.close();
		con.close();
	}
	public void guardarTrack(String file_path, String file_name, String title, int track_number, int album_id, int genero_id, String url, String artwork) throws ClassNotFoundException, SQLException
	{
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(this.url, this.usuario, this.passwd);
		
		PreparedStatement ps = con.prepareStatement("INSERT INTO track(file_path, file_name, title, track_number, album_id, genre_id, itunes_url, itunes_artwork_url) VALUES(?,?,?,?,?,?,?,?)");
		
		
		ps.setString(1, file_path);
		ps.setString(2, file_name);
		ps.setString(3, title);
		ps.setInt(4,track_number);
		ps.setInt(5, album_id);
		ps.setInt(6,genero_id);
		ps.setString(7, url);
		ps.setString(8,artwork);		

		ps.execute();
		
		ps.close();
		con.close();
	}
	public void guardarCancion(CancionMP3 cancion) throws ClassNotFoundException, SQLException
	{		
		int idArtista = buscarArtista(cancion.getAutor());
		if(idArtista == -1)
		{
			guardarArtista(cancion.getAutor());
		}

		int idAlbum = buscarAlbum(cancion.getAlbum());
		if(idAlbum == -1)
		{
			guardarAlbum(cancion.getAlbum(), cancion.getAnyo(), idArtista);
		}
		if(buscarCancion(cancion.getTitulo()) == null)
		{
			guardarTrack(cancion.getRuta(), cancion.getNombreArchivo(),cancion.getTitulo(), 0, idAlbum, cancion.getGenero(), cancion.getEnlaceItunes(), cancion.getCaratula());
		}	
	}
	public void guardarCancion(List<CancionMP3> canciones) throws ClassNotFoundException, SQLException
	{		
		canciones.forEach(c -> {
			try {
				guardarCancion(c);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		});
	}	
}
