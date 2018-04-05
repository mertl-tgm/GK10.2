package main.webapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.google.gson.Gson;

@Path("/register")
public class Register {
	private Security sec;
	private String vorname = "";
	private String nachname = "";
	private String email = "";
	private Gson gson;
	
	public Register() {
		this.sec = new Security();
		this.gson = new Gson();
	}
	
	@GET 
	@Produces("application/json")
	public String register(	@QueryParam("vorname") String vorname, 
							@QueryParam("nachname") String nachname,
							@QueryParam("email") String email,
							@QueryParam("passwort") String passwort,
							@QueryParam("passwort1") String passwort1) throws Exception {
		String[] response = new String[2];
		response[0] = "error";
	
		this.vorname = vorname;
		this.nachname = nachname;
		this.email = email;
		
		boolean inputFalse = false;
		
		//Checks if vorname and nachname exists
		if (vorname == null||vorname.length() < 1) {
			response[1] += "Vorname muss vorhanden sein.<br>";
			inputFalse = true;
		} else {
			//Checks if vorname only contain a-z, A-Z
			for (char c : vorname.toCharArray()) {
				if (!Character.isAlphabetic(c)) {
					response[1] += "Vorname darf nur aus a-z/A-Z bestehen.<br>";
					inputFalse = true;
					break;
				}
			}
		}
		if (nachname == null||nachname.length() < 1) {
			response[1] += "Nachname muss vorhanden sein.<br>";
			inputFalse = true;
		} else {
			//Checks if nachname only contain a-z, A-Z
			for (char c : nachname.toCharArray()) {
				if (!Character.isAlphabetic(c)) {
					response[1] += "Nachname darf nur aus a-z/A-Z bestehen.<br>";
					inputFalse = true;
					break;
				}
			}
		}
		
		//Check if email is valid
		if (email == null) {
			response[1] += "E-Mail muss vorhanden sein.<br>";
			inputFalse = true;
		} else {
			Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
	        Matcher mat = pattern.matcher(email);
	        if(!mat.matches()) {
	        	response[1] += "E-Mail hat ein ungültiges Format (max@musterman.at)<br>";
	        	inputFalse = true;
	        }
		}
		
		//Check if passwort is the same and a minimum length of 5 characters
		if (passwort == null) {
			response[1] += "Passwort muss vorhanden sein.<br>";
			inputFalse = true;
		} else {
			if (passwort1 == null) {
				response[1] += "Passwort Wiederholung muss vorhanden sein.<br>";
				inputFalse = true;
			} else {
		        if (passwort.length() < 5) {
		        	response[1] += "Passwort muss mindestens 5 Zeichen lang sein.<br>";
		        	inputFalse = true;
		        } else {
		        	if (!passwort.equals(passwort1)) {
		        		response[1] += "Passwörter stimmen nicht überein.<br>";
			        	inputFalse = true;
			        }
		        }
			}
		}
		if (inputFalse) return this.gson.toJson(response);
		
		//encrypt the passwort
		String pw = this.sec.encrypt(passwort);
		if (pw.equals("error")) {
			response[1] += "Fehler beim Verschlüsseln des Passwortes aufgetreten.<br>";
			return this.gson.toJson(response);
		}
		
		//check if email(primary key) is already in db
		DBIO.getInstance().createCon();
		if (DBIO.getInstance().existsIDAlready(email)) {
			DBIO.getInstance().closeCon();
			response[1] += "E-Mail-Adresse bereits im System vorhanden.<br>";
			return this.gson.toJson(response);
		}
		DBIO.getInstance().closeCon();
		
		//saves the user in the db
		DBIO.getInstance().createCon();
		if (!DBIO.getInstance().saveUser(this.email, this.vorname, this.nachname, pw)) {
			DBIO.getInstance().closeCon();
			response[1] += "Fehler beim Speichern in die Datenbank aufgetreten.<br>";
			return this.gson.toJson(response);
		}
		DBIO.getInstance().closeCon();
		
		response[0] = "success";
		return this.gson.toJson(response);
	}
	
}
