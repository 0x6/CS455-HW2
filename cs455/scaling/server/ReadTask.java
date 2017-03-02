package cs455.scaling.server;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ReadTask extends Task {
    public ReadTask(SelectionKey key){
        super(key);
    }

    public void execute() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(8000);

        SocketChannel channel = (SocketChannel) key.channel();
        channel.read(buffer);

        System.out.println(new String(buffer.array()));
    }
}
