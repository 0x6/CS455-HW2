package cs455.scaling.server;


import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WriteTask extends Task {
    ByteBuffer buffer = ByteBuffer.allocate(8000);
    byte[] message;

    public WriteTask(SelectionKey key, byte[] message){
        super(key);
        this.message = message;
    }

    public void execute() throws IOException {
        buffer = ByteBuffer.wrap(message);

        SocketChannel channel = (SocketChannel) key.channel();
        channel.write(buffer);

        Server.serverStats.incrementThroughput();
    }
}
