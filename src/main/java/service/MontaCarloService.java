package service;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
 
@Path("/monte")
public class MontaCarloService {
 
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") Integer count) {

		int dropsHit = 0;
		long startTime = System.currentTimeMillis();

		for (int i = 0; i < count; ++i) {
			double x = Math.random()*2-1.0;
			double y = Math.random()*2-1.0;

			if (Math.hypot(x, y) <= 1) {
				dropsHit++;
			}
		}
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		double pi = 4 * (double)dropsHit / count;
		String result = "<MonteCarlo>" +
				"<Result>" +
				"<Pi>" +
				"<h1>calculate PI</h1>"+
				"<Name>" + "Pi is : " + "</Name>" +
				"<Value>" + pi + "</Value><br>" +
				"<Name>" + "Time needed is : " + "</Name>" +
				"<Duration>"+ elapsedTime + "ms</Duration>"+
				"</Pi></Result></MonteCarlo>";
		return Response.status(200).entity(result).build();
 
	}
 
}