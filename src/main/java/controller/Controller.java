package controller;

import businessLogic.SelectionPolicy;
import businessLogic.SimulationManager;
import view.SimView;
import view.View;


public class Controller {
    private static View view;
    private static SimView simView;

    public Controller(SimView simView, View view, String result) {
        this.simView = simView;
        this.view = view;

        view.startSimActionListener(e -> {
            SelectionPolicy strategy = view.getStrategyPick();

            if(checkInt(view.getClientsNo()) && checkInt(view.getMaxArrival()) && checkInt(view.getMinArrival()) && checkInt(view.getMaxService()) && checkInt(view.getMinService()) && checkInt(view.getQueuesNo()) && checkInt(view.getSimInt())) {
                int timeLimit = Integer.parseInt(view.getSimInt());
                int maxServiceTime = Integer.parseInt(view.getMaxService());
                int minServiceTime = Integer.parseInt(view.getMinService());
                int queuesNo = Integer.parseInt(view.getQueuesNo());
                int clientsNo = Integer.parseInt(view.getClientsNo());
                int minArrivalTime = Integer.parseInt(view.getMinArrival());
                int maxArrivalTime = Integer.parseInt(view.getMaxArrival());

                if(maxServiceTime < minServiceTime) {
                    view.errorResult("Max service time smaller than min service time.");
                    return;
                }

                if(maxArrivalTime < minArrivalTime) {
                    view.errorResult("Max arrival time smaller than min arrival time.");
                    return;
                }

                SimulationManager simulation = new SimulationManager(timeLimit, maxServiceTime, minServiceTime, queuesNo, clientsNo, minArrivalTime, maxArrivalTime, strategy, result);
                Thread th = new Thread(simulation);
                th.start();
                simView.setVisible(true);
            }
        });
    }

    public static void finalResult(String result) {
        simView.setResultPane(result);
    }

    public static void finalAverage(String average) {
        simView.finalResult(average);
    }

    public boolean checkInt(String nr) {
        try {
            int n = Integer.parseInt(nr);
        } catch (NumberFormatException e){
            view.errorResult("Check fields for wrong input.");
            return false;
        }
        return true;
    }
}
