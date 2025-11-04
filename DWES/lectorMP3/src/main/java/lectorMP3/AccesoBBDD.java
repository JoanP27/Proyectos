package lectorMP3;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	
	public CancionMP3 buscarCancion(String nombre) throws ClassNotFoundException, SQLException
	{
		
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(this.url, this.usuario, this.passwd);
		
		PreparedStatement ps=con.prepareStatement("select * from track where title = ?");
		ps.setString(1, nombre);
		ResultSet rs=ps.executeQuery();
		
		
		
		
		while(rs.next()){
			System.out.println(rs.getInt(1)+" "+rs.getString(2));
		}
		
		
		ps.close();
		con.close();
		return null;
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
	public void buscarPor(String tabla, String columna, String valor)throws ClassNotFoundException, SQLException
	{
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(this.url, this.usuario, this.passwd);

		PreparedStatement ps=con.prepareStatement("select * from ? where ? = ?");
	}
	public void guardarArtista(String nombre) throws ClassNotFoundException, SQLException
	{
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(this.url, this.usuario, this.passwd);
		
		PreparedStatement ps = con.prepareStatement("INSERT INTO artist(name) VALUES(?)");
		
		ps.setString(1, nombre);
		ps.execute();
		ps.close();
	}
	public void gurdarCancion(CancionMP3 cancion) throws ClassNotFoundException, SQLException
	{		
		if(buscarArtista(cancion.getAutor()) == -1)
		{
			guardarArtista(cancion.getAutor());
		}
	}
	
	public static void consultar() throws ClassNotFoundException,
	SQLException {
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://localhost:5432/music_library";
		String usuario = "postgres";
		String password = "1234";
		Connection con = DriverManager.getConnection(url, usuario, password);
		
		/**
		PreparedStatement stmt=con.prepareStatement("select * from artist");
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
			System.out.println(rs.getInt(1)+" "+rs.getString(2));
		}
		**/
		
		PreparedStatement ps = con.prepareStatement("INSERT INTO artist(id, name) VALUES(?,?)");
		
		Scanner sc = new Scanner(System.in);
		int id=Integer.parseInt(sc.nextLine());
		String name = sc.nextLine();
		ps.setInt(1,id);
		ps.setString(2, name);
		int i = ps.executeUpdate();
		System.out.println(i+" registros afectados");
		con.close();
	}
	
	
}
