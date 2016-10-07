package controller;

import model.MonteCarloDTO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Niklas on 06/10/16.
 */
public class Worker implements Runnable{
    private static String ipAddress = "";
    private static long rounds;
    private MonteCarloDTO monteCarloDTO;
    public Worker() {
    }


    public static String getIpAddress() {
        return ipAddress;
    }

    public static void setIpAddress(String ipAddress) {
        Worker.ipAddress = ipAddress;
    }

    public static long getRounds() {
        return rounds;
    }

    public static void setRounds(long rounds) {
        Worker.rounds = rounds;
    }

    public MonteCarloDTO getMonteCarloDTO() {
        return monteCarloDTO;
    }

    private MonteCarloDTO calculatePIForAllInstances() throws IOException, JAXBException {
        URL url = new URL("http://" + ipAddress+ "/monte/monte" + rounds);
        MonteCarloDTO monteCarloDTOtemp = null;
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int code = connection.getResponseCode();

        if(code == 200){
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            JAXBContext context = JAXBContext.newInstance(MonteCarloDTO.class);
            Unmarshaller unMarshaller = context.createUnmarshaller();
            monteCarloDTOtemp = (MonteCarloDTO) unMarshaller.unmarshal(in);
            in.close();
        }
        return monteCarloDTOtemp;
    }

    @Override
    public void run() {
        try {
            monteCarloDTO = calculatePIForAllInstances();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}

