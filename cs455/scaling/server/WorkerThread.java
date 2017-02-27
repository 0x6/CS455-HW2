package cs455.scaling.server;

public class WorkerThread extends Thread {

    Task task;
    boolean isActive;

    public WorkerThread() {
        isActive = false;
        task = null;
    }

    public void setTask(Task task){
        this.task = task;
        this.isActive = true;
    }

    public boolean isActive(){
        return isActive;
    }

    @Override
    public void run() {
        try {
            while (true) {
                while (task != null) {
                    task.execute();
                    isActive = false;
                    task = null;
                }
                Thread.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}