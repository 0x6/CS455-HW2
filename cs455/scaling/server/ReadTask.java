package cs455.scaling.server;


import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        int temp = channel.read(buffer);

        key.interestOps(SelectionKey.OP_READ);

        this.taskList.add(new WriteTask(this.key, hash(buffer.array())));
    }

    public byte[] hash(byte[] data){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            byte[] dataHash = digest.digest(data);
            BigInteger hashInt = new BigInteger(1, dataHash);

            return hashInt.toString(16).getBytes();
        } catch (NoSuchAlgorithmException e){
            System.out.println("Algorithm does not exist: " + e);
        }

        return new byte[0];
    }
}
