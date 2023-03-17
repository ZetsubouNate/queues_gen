package businessLogic;

import controller.Controller;
import model.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationManager implements Runnable {

    private int timeLimit;
    private int maxServiceTime;
    private int minServiceTime;
    private int queuesNo;
    private int clientsNo;
    private int minArrivalTime;
    private int maxArrivalTime;
    private SelectionPolicy policy;
    private Double averageService;
    private Double averageWaiting;
    private Scheduler scheduler;
    private List<Task> generatedTasks = new ArrayList<>();
    private int peakHour;
    private List<Thread> threads = new ArrayList<>();
    private String result;
    private boolean guiON = false;
    private int maxPeakWaiting = 0;


    public SimulationManager(int timeLimit, int maxServiceTime, int minServiceTime, int queuesNo, int clientsNo, int minArrivalTime, int maxArrivalTime, SelectionPolicy policy, String result) {
        this.guiON = true;
        this.timeLimit = timeLimit;
        this.maxServiceTime = maxServiceTime;
        this.minServiceTime = minServiceTime;
        this.queuesNo = queuesNo;
        this.clientsNo = clientsNo;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.policy = policy;
        this.scheduler = new Scheduler(queuesNo, clientsNo);
        this.result = result;
        File out = new File(result);
        try {
            out.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < this.queuesNo; i++) {
            Thread th = new Thread(scheduler.getServers().get(i));
            threads.add(th);
            th.start();
        }

        scheduler.changeStrategy(this.policy);
        generateRandomTasks();
        double totalServiceTime = 0;
        for (Task generatedTask : generatedTasks) {
            totalServiceTime += generatedTask.getArrivalTime();
        }
        this.averageService = totalServiceTime / clientsNo;
    }

    public SimulationManager(String in, String out) throws FileNotFoundException {
        this.result = out;
        File fin = new File(in);
        File fout = new File(result);
        Scanner scan = new Scanner(fin);
        try {
            fout.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.clientsNo = Integer.parseInt(scan.nextLine());
        this.queuesNo = Integer.parseInt(scan.nextLine());
        this.timeLimit = Integer.parseInt(scan.nextLine());
        String st = scan.nextLine();
        String[] s = st.split(" ");
        this.minArrivalTime = Integer.parseInt(s[0]);
        this.maxArrivalTime = Integer.parseInt(s[1]);
        st = scan.nextLine();
        s = st.split(" ");
        this.minServiceTime = Integer.parseInt(s[0]);
        this.maxServiceTime = Integer.parseInt(s[1]);
        this.scheduler = new Scheduler(queuesNo, clientsNo);
        scan.close();
        for (int i = 0; i < this.queuesNo; i++) {
            Thread th = new Thread(scheduler.getServers().get(i));
            threads.add(th);
            th.start();
        }

        scheduler.changeStrategy(SelectionPolicy.SHORTEST_QUEUE);
        generateRandomTasks();
        double totalServiceTime = 0;
        for (Task generatedTask : generatedTasks) {
            totalServiceTime += generatedTask.getArrivalTime();
        }
        this.averageService = totalServiceTime / clientsNo;
    }

    private void generateRandomTasks() {
        for (int i = 0; i < this.clientsNo; i++) {
            Random n = new Random();
            int serviceTime = n.nextInt(maxServiceTime + 1 - minServiceTime) + minServiceTime;
            int arrivalTime = n.nextInt(maxArrivalTime + 1 - minArrivalTime) + minArrivalTime;
            this.generatedTasks.add(new Task(i, arrivalTime, serviceTime));
        }
        generatedTasks.sort(Comparator.comparing(Task::getArrivalTime));
    }

    @Override
    public void run() {
        String simResult = "";
        String progress = "";
        int currentTime = 0;

        while (currentTime < this.timeLimit) {
            simResult = "Time: " + currentTime + "\n";
            progress += "Time: " + currentTime + "\n";
            for (int i = 0; i < generatedTasks.size(); i++) {
                if (generatedTasks.get(i).getArrivalTime() == currentTime) {
                    scheduler.dispatchTask(generatedTasks.get(i));
                    generatedTasks.remove(i);
                    i--;
                }
            }
            simResult += "Waiting clients: ";
            progress += "Waiting clients: ";
            for (int i = 0; i < generatedTasks.size(); i++) {
                if (i % 5 == 0)
                    simResult += "\n";
                simResult += "( " + generatedTasks.get(i).getID() + ", " + generatedTasks.get(i).getArrivalTime() + ", " + generatedTasks.get(i).getServiceTime() + " ); ";
                progress += "( " + generatedTasks.get(i).getID() + ", " + generatedTasks.get(i).getArrivalTime() + ", " + generatedTasks.get(i).getServiceTime() + " ); ";
            }
            simResult += "\n";
            progress += "\n";
            for (int i = 0; i < queuesNo; i++) {
                simResult += scheduler.getServers().get(i).toString() + "\n";
                progress += scheduler.getServers().get(i).toString() + "\n";
                if (!scheduler.getServers().get(i).getTasks().isEmpty()) {
                    scheduler.getServers().get(i).setWaitingPeriod(new AtomicInteger(scheduler.getServers().get(i).getWaitingPeriod().decrementAndGet()));
                    scheduler.getServers().get(i).getTasks().peek().setServiceTime(scheduler.getServers().get(i).getTasks().peek().getServiceTime() - 1);
                }
            }

            int peakWaiting = 0;
            for (int i = 0; i < queuesNo; i++) {
                peakWaiting += scheduler.getServers().get(i).getWaitingPeriod().intValue();
            }

            if (maxPeakWaiting < peakWaiting) {
                maxPeakWaiting = peakWaiting;
                peakHour = currentTime;
            }

            currentTime++;

            int closedQueues = 0;
            for (int i = 0; i < queuesNo; i++) {
                if (scheduler.getServers().get(i).getTasks().isEmpty() && generatedTasks.isEmpty())
                    closedQueues++;
            }

            if (guiON)
                Controller.finalResult(simResult);

            if (closedQueues == queuesNo || currentTime == timeLimit) {
                progress += "\n End of Simulation.\n";
                break;
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
            double waitingSum = 0;
            for (int i = 0; i < queuesNo; i++) {
                waitingSum += scheduler.getServers().get(i).getWaitingPeriodSum().intValue();
            }
            averageWaiting = waitingSum / clientsNo / queuesNo;

            simResult = "Average waiting time: " + String.format("%.1f", averageWaiting) + "\n";
            simResult += "Average service time: " + String.format("%.1f", averageService) + "\n";
            simResult += "Peak hour: " + peakHour;

            try {
                FileWriter writer = new FileWriter(result);
                writer.write(progress);
                writer.write(simResult);
                writer.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
                if (guiON)
                    Controller.finalAverage(simResult);
        }

}


