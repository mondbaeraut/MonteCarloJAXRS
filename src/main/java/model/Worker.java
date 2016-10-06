package model;

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
public class Worker {
    private static String ipAddress = "";
    private static long rounds;
    public Worker() {
    }

    public static String getIpAddress() {
        return ipAddress;
    }

    public static void setIpAddress(String ipAddress) {
        Worker.ipAddress = ipAddress;
    }

    private MonteCarloDTO calculatePIForAllInstances() throws IOException, JAXBException {
        URL url = new URL("http://" + ipAddress+ "/monte/monte" + rounds);
        MonteCarloDTO monteCarloDTO = null;
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int code = connection.getResponseCode();

        if(code == 200){
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            JAXBContext context = JAXBContext.newInstance(MonteCarloDTO.class);
            Unmarshaller unMarshaller = context.createUnmarshaller();
            monteCarloDTO = (MonteCarloDTO) unMarshaller.unmarshal(in);
            in.close();
        }
        return monteCarloDTO;
    }
}

