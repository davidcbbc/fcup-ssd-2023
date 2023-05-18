import io.grpc.Server;
import io.grpc.ServerBuilder;
import kademlia.KademliaNode;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {


        BigInteger uid = new BigInteger("1234567890123456789012345678901234567890",16);

        System.out.println(uid.hashCode());


        KademliaNode kademliaNode = new KademliaNode("localhost",uid,8080,null);
        

    }
}
