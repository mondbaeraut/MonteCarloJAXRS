package service;
 
import controller.MonteCarloCalculator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Path("/monte")
public class MontaCarloService {
	MonteCarloCalculator calculator = new MonteCarloCalculator();

	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") long shots) {
		ExecutorService exutor = Executors.newFixedThreadPool(10);

		return Response.status(200).entity(calculator.calculatePI(shots)).build();
	}
 
}