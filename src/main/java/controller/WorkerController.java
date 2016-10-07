package controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Niklas on 07/10/16.
 */
public class WorkerController {
    private HashMap<String, Worker> workerHashMap = new HashMap<String, Worker>();

    public Collection<Worker> getWorker(){
        return workerHashMap.values();
    }

    public boolean registerAddress(String ip){
        if(ip != "" && !workerHashMap.containsKey(ip)){
            Worker worker = new Worker();
            worker.setIpAddress(ip);
            workerHashMap.put(ip,worker);
            return true;
        }
        return false;
    }

    public boolean unregiserAddress(String ip){
        if(ip != "" && workerHashMap.containsKey(ip)){
            workerHashMap.remove(ip);
            return true;
        }
        return false;
    }

    public int getSizeOfWorkerList(){
        return workerHashMap.size();
    }
}
