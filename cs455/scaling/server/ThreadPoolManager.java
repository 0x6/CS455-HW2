package cs455.scaling.server;

import java.util.ArrayList;

public class ThreadPoolManager extends Thread{

    TaskList taskList;
    ArrayList<WorkerThread> workerThreads;

    public ThreadPoolManager(int threadPoolSize, TaskList _taskList){
        taskList = _taskList;
        workerThreads = new ArrayList<WorkerThread>();

        for (int i = 0; i < threadPoolSize; i++){
            WorkerThread workerThread = new WorkerThread();
            workerThread.start();
            this.workerThreads.add(workerThread);
        }
    }

    public WorkerThread getIdleThread(){
        for(WorkerThread wThread: workerThreads){
            if(!wThread.isActive()){
                return wThread;
            }
        }

        return null;
    }

    @Override
    public void run() {
        while(true){
            if(!taskList.isEmpty()){
                Task task = taskList.poll();
                WorkerThread thread = getIdleThread();

                if (thread != null){
                    thread.setTask(task);
                }
            }
        }
    }
}