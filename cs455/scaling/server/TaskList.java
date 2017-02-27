package cs455.scaling.server;

import java.util.ArrayList;

public class TaskList {
    ArrayList<Task> tasks;

    public TaskList(){
        tasks = new ArrayList<Task>();
    }

    public synchronized void add(Task t){
        tasks.add(t);
    }

    public synchronized Task poll(){
        if(tasks.size() < 1)
            return null;

        Task t = tasks.get(0);
        tasks.remove(0);

        return t;
    }

    public synchronized boolean isEmpty(){
        return tasks.isEmpty();
    }
}
