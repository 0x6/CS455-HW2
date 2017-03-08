package cs455.scaling.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server {
    private static Selector selector;
    private static ServerSocketChannel serverSocketChannel;

    private static TaskList taskList;
    private static ThreadPoolManager tpm;
    public static ServerStats serverStats;
    public static int numConnections;

    public static void main(String[] args) throws IOException{
        taskList = new TaskList();

        tpm = new ThreadPoolManager(new Integer(args[1]), taskList);
        tpm.initialize();

        startServer(new Integer(args[0]));
    }

    public static void startServer(final int PORT) throws IOException{
        selector = Selector.open(); //Create selector

        serverSocketChannel = ServerSocketChannel.open(); //Create socket channel, configure blocking, and bind
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(PORT));

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); //Register channel to selector

        System.out.println("Server running at " + InetAddress.getLocalHost().getHostAddress() + ":" + PORT);

        ByteBuffer buffer = ByteBuffer.allocate(8000);

        serverStats = new ServerStats();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                serverStats.printStats(numConnections);
            }
        }, 5000, 5000);

        numConnections = 0;
        while(true){
            selector.select();

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while(iterator.hasNext()){
                SelectionKey key = iterator.next();

                if(key.isAcceptable()){
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);

                    numConnections++;
                }
                if(key.isReadable()){
                    key.interestOps(SelectionKey.OP_WRITE);
                    taskList.add(new ReadTask(key, taskList));
                }

                iterator.remove();
            }
        }
    }
}
