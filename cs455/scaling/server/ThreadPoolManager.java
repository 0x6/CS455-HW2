package cs455.scaling.server;

import java.util.ArrayList;

public class ThreadPoolManager {
    private ArrayList<WorkerThread> workerThreads;

    public ThreadPoolManager(int threadPoolSize, TaskList taskList){
        workerThreads = new ArrayList<WorkerThread>();

        for (int i = 0; i < threadPoolSize; i++){
            this.workerThreads.add(new WorkerThread(taskList));
        }
    }

    public void initialize(){
        for(WorkerThread worker: workerThreads){
            worker.start();
        }
    }
}