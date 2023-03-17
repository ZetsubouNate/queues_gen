package businessLogic;

import model.Server;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        this.servers = new ArrayList<>();
        for(int i = 0; i < this.maxNoServers; i++) {
            this.servers.add(new Server(i + 1));
        }
    }

    public void changeStrategy(SelectionPolicy policy) {
        if(policy.equals(SelectionPolicy.SHORTEST_QUEUE)) {
            this.strategy = new ShortestQueueStrategy();
        }
        if(policy.equals(SelectionPolicy.SHORTEST_TIME)) {
            this.strategy = new TimeStrategy();
        }
    }

    public void dispatchTask(Task t) {
        strategy.addTask(servers, t);
    }

    public List<Server> getServers() {
        return servers;
    }
}
