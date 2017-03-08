package cs455.scaling.server;

public class ServerStats {
    private double throughput = 0;

    public ServerStats(){}

    public synchronized void incrementThroughput(){
        throughput += 1;
    }

    public synchronized void printStats(int connections){
        System.out.println("[" + System.currentTimeMillis() + "] Current Server Throughput: " + (throughput/5) + " messages/s, Active Client Connections: " + connections);
        throughput = 0;
    }
}
