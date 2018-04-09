package main.webapp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("/login")
public class Login {
	private Security sec;
	private String email = "";
	private Gson gson;
	
	public Login() {
		this.sec = new Security();
		this.gson = new Gson();
	}
	
	@GET 
	@Produces("application/json")
	public String submit(	@QueryParam("email") String email,
							@QueryParam("pw") String passwort) {
		String[] response = new String[2];
		response[0] = "error";
		response[1] = "";
		
		this.email = email;
		String vorname = "";
		String nachname = "";
		
		//Check if email is valid
		if (email == null) {
			response[1] += "E-Mail muss vorhanden sein.<br>";
			return this.gson.toJson(response);
		} else {
			Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
	        Matcher mat = pattern.matcher(email);
	        if(!mat.matches()) {
	        	response[1] += "E-Mail hat ein ungültiges Format (max@musterman.at)<br>";
	        	return this.gson.toJson(response);
	        }
		}
		
		//reads the benutzer with the same id from the db
		try {
			DBIO.getInstance().createCon();
			ResultSet res = DBIO.getInstance().readUser(email);
			if (res == null) {
				response[1] += "Fehler beim Abrufen der Datenbank.<br>";
				return this.gson.toJson(response);
			}
			
			//checks if a benutzer with the id is returned
			if (!res.next()) {
				response[1] += "E-Mail-Adresse im System nicht vorhanden.<br>";
				return this.gson.toJson(response);
			} else {
				//decrypts the password and compare it to the given one
				String pw = this.sec.decrypt(res.getString("pw"));
				if (pw.equals("error")) {
					response[1] += "Fehler beim Entschlüsseln des Passwortes aufgetreten.<br>";
					return this.gson.toJson(response);
				}
				if (passwort == null) {
					response[1] += "Passwort muss vorhanden sein.<br>";
					return this.gson.toJson(response);
				}
				if (!passwort.equals(pw)) {
					response[1] += "Passwort ist falsch eingegeben worden.<br>";
					return this.gson.toJson(response);
				} else {
					vorname = res.getString("vname");
					nachname = res.getString("nname");
				}
			}
			DBIO.getInstance().closeCon();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response[0] = "success";
		response[1] = vorname + "#" + nachname + "#" + email;
		return this.gson.toJson(response);
	}
}
