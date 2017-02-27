package cs455.scaling.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadLocalRandom;

public class Client {
    public static SocketChannel socketChannel;

    public static void main(String[] args) throws IOException, InterruptedException{
        socketChannel = SocketChannel.open(new InetSocketAddress(args[0], new Integer(args[1])));

        byte[] b = {100, 100, 100, 100};
        ByteBuffer buffer = ByteBuffer.wrap(b);

        socketChannel.write(buffer);

        while(true){
            Thread.sleep(1000/Integer.parseInt(args[2]));
        }
    }

    public byte[] getRandomBytes(int length){
        byte[] b = new byte[length];

        for(int i = 0; i < length; i++){
            b[i] = (byte)ThreadLocalRandom.current().nextInt(-128, 128);
        }

        return b;
    }
}
