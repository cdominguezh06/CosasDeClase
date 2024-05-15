

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	//Libreria
	private String driver;
		
	//BD
	private String database;
		
	//Host
	private String hostname;
		
	//Puerto
	private String port;
		
	//Usuario
	private String username;
	
	//Clave de usuario
	private String password;
	
	//Ruta de nuestra base de datos
	private String url;
	
	public Conexion(String database) {
		super();
		this.database = database;
		this.driver = "com.mysql.jdbc.Driver";
		this.hostname = "localhost";
		this.port = "3306";
		this.username = "root";
		this.password = "";
		this.url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
	}

	
	
	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}
	
	public Connection conectarMySQL() {
		Connection conn = null;
		try {
			Class.forName(driver);
		    conn = DriverManager.getConnection(url, username, password);
		 } catch (ClassNotFoundException | SQLException e) {
			 e.printStackTrace();
		 }
		return conn;
		 }
	}

