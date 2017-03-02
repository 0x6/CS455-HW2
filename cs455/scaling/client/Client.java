package cs455.scaling.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadLocalRandom;

public class Client {
    final static int LENGTH = 10;
    public static SocketChannel socketChannel;

    public static void main(String[] args) throws IOException, InterruptedException{
        socketChannel = SocketChannel.open(new InetSocketAddress(args[0], new Integer(args[1])));

        ByteBuffer buffer = ByteBuffer.allocate(LENGTH);

        Thread.sleep(5000);
        while(true){
            buffer.clear();
            buffer.wrap(getRandomBytes(LENGTH));

            System.out.println();
            socketChannel.write(buffer);

            Thread.sleep(5000);
            //Thread.sleep(1000/Integer.parseInt(args[2]));
        }
    }

    public static byte[] getRandomBytes(int length){
        byte[] b = new byte[length];

        for(int i = 0; i < length; i++){
            b[i] = (byte)ThreadLocalRandom.current().nextInt(-128, 128);
        }

        return b;
    }
}
