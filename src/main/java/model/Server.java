package model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{

    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private AtomicInteger waitingPeriodSum;
    private int n;

    public Server(int n) {
        this.n = n;
        this.tasks = new LinkedBlockingQueue<Task>();
        this.waitingPeriod = new AtomicInteger(0);
        this.waitingPeriodSum = new AtomicInteger(0);
    }

    public AtomicInteger getWaitingPeriodSum() {
        return waitingPeriodSum;
    }

    public void setWaitingPeriodSum(AtomicInteger waitingPeriodSum) {
        this.waitingPeriodSum = waitingPeriodSum;
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public void setTasks(BlockingQueue<Task> tasks) {
        this.tasks = tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
        waitingPeriod.getAndAdd(newTask.getServiceTime());
        waitingPeriodSum.addAndGet(waitingPeriod.intValue());
    }

    @Override
    public void run() {
        while(!tasks.isEmpty()) {
            if (this.tasks.peek().getServiceTime() > 0) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else
                this.tasks.poll();
        }
    }

    @Override
    public String toString() {
        String result = "";
        if(!this.tasks.isEmpty()) {
            result = result + ("Queue " + n + "  ");
            for(Task task: this.tasks) {
                result = result + "(" + task.getID() + ", " + task.getArrivalTime() + ", " + task.getServiceTime() + ");";
            }
        }
        else
            result = result + ("Queue " + n + " closed");

        return result;
    }
}
