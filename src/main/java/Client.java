import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import kademlia.KademliaNode;

import java.math.BigInteger;

public class Client {

    public static void main(String[] args) {
        BigInteger uid = new BigInteger("1234567890123456789012345678901234567892",16);

        System.out.println(uid.hashCode());


        KademliaNode kademliaNode = new KademliaNode("localhost",uid,8081,null);

        kademliaNode.pingNode("localhost",8080);


    }



}
