package cs455.scaling.server;


import java.nio.channels.SelectionKey;

public class ReadTask extends Task {
    public ReadTask(SelectionKey key){
        super(key);
    }

    public void execute(){
        System.out.println("Hello");
    }
}
