package cs455.scaling.server;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ch73168 on 3/7/17.
 */
public class Test {
    public static void main(String [] args){
        ByteBuffer buffer = ByteBuffer.allocate(8000);
        System.out.println(hash(buffer.array()));
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

        return "nohash";
    }
}
