package cs455.scaling.server;

import java.util.ArrayList;

public class TaskList {
    ArrayList<Task> tasks;

    public TaskList(){
        tasks = new ArrayList<Task>();
    }

    public synchronized void add(Task t){
        tasks.add(t);
        notify();
    }

    public synchronized Task poll() throws InterruptedException{
        while(tasks.size() < 1)
            wait(10);

        Task t = tasks.get(0);
        tasks.remove(0);

        return t;
    }

    public synchronized boolean isEmpty(){
        return tasks.isEmpty();
    }
}
