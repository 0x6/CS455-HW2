package cs455.scaling.server;


import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class ReadTask extends Task {
    ByteBuffer buffer = ByteBuffer.allocate(8000);
    TaskList taskList;

    public ReadTask(SelectionKey key, TaskList taskList){
        super(key);
        this.taskList = taskList;
    }

    public void execute() throws IOException {
        buffer.clear();
        buffer.rewind();

        SocketChannel channel = (SocketChannel) key.channel();
        if(handleFragment(channel.read(buffer))){
            this.taskList.add(new WriteTask(this.key, hash(buffer.array())));
        }

        key.interestOps(SelectionKey.OP_READ);
    }

    public boolean handleFragment(int length){
        byte[] fragment;
        if(key.attachment() == null)
            fragment = new byte[0];
        else
            fragment = (byte[])key.attachment();

        byte[] product = new byte[fragment.length + length];
        System.arraycopy(fragment, 0, product, 0, fragment.length);
        System.arraycopy(buffer.array(), 0, product, fragment.length, length);

        if(product.length < 8000){
            key.attach(product);
            return false;
        }
        if(product.length == 8000){
            buffer = ByteBuffer.wrap(product);
            key.attach(null);

            return true;
        }

        return false;
    }

    public byte[] hash(byte[] data){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            byte[] dataHash = digest.digest(data);
            BigInteger hashInt = new BigInteger(1, dataHash);

            //System.out.println(hashInt.toString(16));
            return hashInt.toString(16).getBytes();
        } catch (NoSuchAlgorithmException e){
            System.out.println("Algorithm does not exist: " + e);
        }

        return new byte[0];
    }
}
