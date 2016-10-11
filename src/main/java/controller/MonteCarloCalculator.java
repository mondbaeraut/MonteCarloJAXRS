package controller;

import model.MonteCarloDTO;

/**
 * Created by Niklas on 06/10/16.
 *
 */
public class MonteCarloCalculator {

    public MonteCarloCalculator() {
    }
    public MonteCarloDTO calculatePI(long shots){
        int dropsHit = 0;
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < shots; i++) {
            double x = Math.random();
            double y = Math.random();

            if (Math.hypot(x, y) <= 1) {
                dropsHit++;
            }
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        MonteCarloDTO monteCarloDTO = new MonteCarloDTO();
        monteCarloDTO.setPi(4 * (double)dropsHit / shots);
        monteCarloDTO.setDuration(elapsedTime);
        return  monteCarloDTO;
    }
}
