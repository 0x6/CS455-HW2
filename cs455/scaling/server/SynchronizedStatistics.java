package cs455.scaling.server;

public class SynchronizedStatistics {
    private int totalSent = 0, totalReceived = 0;

    public SynchronizedStatistics(){}

    public synchronized void incrementSent(){
        totalSent++;
    }

    public synchronized void incrementReceived(){
        totalReceived++;
    }

    public synchronized void printStats(){
        System.out.println("[" + System.currentTimeMillis() + "] Total Sent Count: " + totalSent + ", Total Received Count: " + totalReceived);
        totalSent = 0;
        totalReceived = 0;
    }
}
