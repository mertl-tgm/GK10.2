package main.webapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
public class DBIO {
	private static DBIO instance = null;
	private Connection con;
	
	public static DBIO getInstance() {
		if (instance == null) instance = new DBIO();
		return instance;
	}
	
	public void createCon() throws Exception {
		Class.forName("org.h2.Driver");
		con = DriverManager.getConnection("jdbc:h2:~/ertldb", "sa", "");
		createTable();
	}
	
	public void createTable() throws Exception {
		Statement stmt = con.createStatement(); 
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS benutzer(id VARCHAR PRIMARY KEY, vname VARCHAR(255), nname VARCHAR(255), pw VARCHAR(255))");
	}
	
	public void closeCon() throws Exception {
		con.close();
	}
	
	public void dropTable() throws Exception {
		Statement stmt = con.createStatement(); 
		stmt.executeUpdate("DROP TABLE IF EXISTS benutzer");
	}
	
	public boolean existsIDAlready(String id) {
		try {
			Statement select = con.createStatement();
			ResultSet res = select.executeQuery("select * from benutzer where id ='" + id + "'");
			if (res.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean saveUser(String email, String vorname, String nachname, String pw) {
		String sql = "insert into benutzer values(?, ?, ?, ?)";
		try {
			PreparedStatement stmt = this.con.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, vorname);
			stmt.setString(3, nachname);
			stmt.setString(4, pw);
			if (stmt.executeUpdate() != 1) return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public ResultSet readUser(String email) {
		try {
			Statement stmt = this.con.createStatement();
			return stmt.executeQuery("select * from benutzer where id ='" + email + "'");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}