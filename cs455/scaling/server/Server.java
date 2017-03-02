package cs455.scaling.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server {
    private static Selector selector;
    private static ServerSocketChannel serverSocketChannel;

    private static TaskList taskList;
    private static ThreadPoolManager tpm;

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

        System.out.println("cs455.scaling.server.server running on port " + PORT);

        while(true){
            selector.select();

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            System.out.println(selector.selectedKeys().size());
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();

                if(key.isAcceptable()){
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                if(key.isReadable()){
                    //System.out.println("Read request.");

                    SocketChannel socketChannel = (SocketChannel) key.channel();


                    //taskList.add(new ReadTask(key));
                }

                iterator.remove();
            }

            try{
                Thread.sleep(2000);
            } catch (Exception e){
                System.out.println();
            }
        }
    }
}
