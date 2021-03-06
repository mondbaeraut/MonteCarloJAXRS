package service;
 
import controller.MonteCarloCalculator;
import controller.Worker;
import controller.WorkerController;
import model.MonteCarloDTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Path("/monte")
public class MontaCarloService {
	MonteCarloCalculator calculator = new MonteCarloCalculator();

	@GET
	@Path("/calc/{param}")
	public Response calculate(@PathParam("param") long shots) {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		if(WorkerController.getInstance().getSizeOfWorkerList() > 0) {
			for (Worker worker : WorkerController.getInstance().getWorker()) {
				worker.setRounds(shots / WorkerController.getInstance().getSizeOfWorkerList());
				executor.execute(worker);
			}
			// Wait until all threads are finish
			List<MonteCarloDTO> responseList = new LinkedList<MonteCarloDTO>();
			while (responseList.size() != WorkerController.getInstance().getWorker().size()) {
				for (Worker worker : WorkerController.getInstance().getWorker()) {
					if (worker.getMonteCarloDTO() != null && !worker.getMonteCarloDTO().getWasProcessed()) {
						worker.getMonteCarloDTO().setWasProcessed(true);
						responseList.add(worker.getMonteCarloDTO());
					}
				}
			}

			executor.shutdown();
			double pi = 0;
			long duration = 0;
			for (MonteCarloDTO responseDTO : responseList) {
				pi += responseDTO.getPi();
				duration += responseDTO.getDuration();
			}

			MonteCarloDTO result = new MonteCarloDTO();
			result.setDuration(duration / responseList.size());
			result.setPi(pi / (double)responseList.size());
			return Response.status(200).entity(result).build();
		}
		return Response.status(200).entity("Size of QUEUE = 0").build();
	}
 	@GET
	@Path("/register/{ip}")
	public Response register(@PathParam("ip") String ipAdress){
		WorkerController.getInstance().registerAddress(ipAdress);
		return Response.status(200).entity("IP: "+ipAdress + " registered").build();
	}
	@GET
	@Path("/unregister/{ip}")
	public Response unregister(@PathParam("ip") String ipAdress){
		WorkerController.getInstance().unregiserAddress(ipAdress);
		return Response.status(200).entity("IP: "+ipAdress + " unregistered").build();
	}
	@GET
	@Path("/slave/{shots}")
	public Response calculatePi(@PathParam("shots") long shots){
		MonteCarloCalculator calculator = new MonteCarloCalculator();
		return Response.status(200).entity(calculator.calculatePI(shots)).build();
	}
}