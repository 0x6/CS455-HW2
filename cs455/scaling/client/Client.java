package cs455.scaling.client;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Client {
    final static int LENGTH = 8000;
    public static SocketChannel socketChannel;

    static ArrayList<String> hashes = new ArrayList<String>();


    public static void main(String[] args) throws IOException, InterruptedException{
        socketChannel = SocketChannel.open(new InetSocketAddress(args[0], new Integer(args[1])));

        ByteBuffer buffer = ByteBuffer.allocate(LENGTH);

        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    int length = 0;

                    while(true){
                        ByteBuffer readBuffer = ByteBuffer.allocate(8000);
                        length = socketChannel.read(readBuffer);

                        String b = new String(Arrays.copyOfRange(readBuffer.array(), 0, length));
                        System.out.println(hashes.contains(b) + " " + b);
                    }
                } catch (Exception e){
                    System.out.println("Unable to read from socketChannel.");
                }
            }
        }).start();


        while(true){

            buffer = ByteBuffer.wrap(getRandomBytes(LENGTH));
            hashes.add(hash(buffer.array()));

            socketChannel.write(buffer);
            buffer.clear();

            //Thread.sleep(2500);
            Thread.sleep(1000/Integer.parseInt(args[2]));
        }
    }

    public static String hash(byte[] data){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            byte[] dataHash = digest.digest(data);
            BigInteger hashInt = new BigInteger(1, dataHash);

            return hashInt.toString(16);
        } catch (NoSuchAlgorithmException e){
            System.out.println("Algorithm does not exist: " + e);
        }

        return "Owendavis";
    }

    public static byte[] getRandomBytes(int length){
        byte[] b = new byte[length];

        for(int i = 0; i < length; i++){
            b[i] = (byte)ThreadLocalRandom.current().nextInt(-128, 128);
        }

        return b;
    }
}
