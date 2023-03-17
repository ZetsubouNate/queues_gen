package businessLogic;
import model.*;

import java.util.List;

public class ShortestQueueStrategy implements Strategy{

    @Override
    public void addTask(List<Server> servers, Task t) {
        int smoll = 0;
        for(int i = 0; i < servers.size(); i++) {
            Server s = servers.get(i);
            if(servers.get(smoll).getTasks().size() >= s.getTasks().size())
                smoll = i;

            if(s.getTasks().isEmpty()) {
                Thread th = new Thread(s);
                s.addTask(t);
                th.start();
                return;
            }
        }
        servers.get(smoll).addTask(t);
    }
}
