package cs455.scaling.server;

public class WorkerThread extends Thread {

    Task task;
    TaskList taskList;

    public WorkerThread(TaskList taskList) {
        this.taskList = taskList;
        task = null;
    }

    @Override
    public void run(){
        try{
            while(true){
                task = taskList.poll();

                if(task != null){
                    task.execute();
                    task = null;
                }
            }
        } catch (Exception e){
            System.out.println("Worker thread interrupted. " + e);
        }
    }
}