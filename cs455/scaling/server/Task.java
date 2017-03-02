package cs455.scaling.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public abstract class Task {
    SelectionKey key;

    public Task(SelectionKey _key){
        key = _key;
    }

    public SelectionKey getKey(){
        return key;
    }

    public abstract void execute() throws IOException;
}
