package we.robots.parkme.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import we.robots.parkme.util.CarParkFileHandler;

@Path("/refresh")
public class Refresh {

	@GET
	@Path("/dorefresh")
	@Produces(MediaType.APPLICATION_XML)
	public String refresh(@QueryParam("id") String id) {

		return CarParkFileHandler.readCarPark(id);
	}
}
