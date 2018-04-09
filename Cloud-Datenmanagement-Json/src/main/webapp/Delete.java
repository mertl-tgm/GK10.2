package main.webapp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.google.gson.Gson;

@Path("/delete")
public class Delete {
	private Gson gson;
	
	public Delete() {
		this.gson = new Gson();
	}
	
	@GET 
	@Produces("application/json")
	public String submit(@QueryParam("email") String email) throws Exception {
		String[] response = new String[2];
		response[0] = "error";
		response[1] = "";
		
		//check if email(primary key) is already in db
		DBIO.getInstance().createCon();
		if (!DBIO.getInstance().existsIDAlready(email)) {
			DBIO.getInstance().closeCon();
			response[1] += "E-Mail-Adresse nicht im System vorhanden.<br>";
			return this.gson.toJson(response);
		}
		if (!DBIO.getInstance().deleteUser(email)) {
			response[1] += "Fehler beim Löschen aus der Datenbank.<br>";
			return this.gson.toJson(response);
		}
		DBIO.getInstance().closeCon();
		
		response[0] = "success";
		return this.gson.toJson(response);
	}
}
