import io.grpc.Server;
import io.grpc.ServerBuilder;
import kademlia.KademliaNode;
import kademlia.Util;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Main {

    public static void main(String[] args) throws InterruptedException {



        KademliaNode kademliaNode1 = Util.createNewNode();

        KademliaNode kademliaNode2 = Util.createNewNode();

        KademliaNode kademliaNode3 = Util.createNewNode();

        kademliaNode1.getRoutingTable().update(kademliaNode2);
        kademliaNode1.getRoutingTable().update(kademliaNode3);

        kademliaNode2.getRoutingTable().update(kademliaNode1);
        kademliaNode2.getRoutingTable().update(kademliaNode3);

        kademliaNode3.getRoutingTable().update(kademliaNode2);
        kademliaNode3.getRoutingTable().update(kademliaNode1);

        Thread.sleep(1000); // sleep to wait until the nodes start the listener

        System.out.println(kademliaNode1.getRoutingTable().findClosestNodes(kademliaNode3.getId(),2));

        kademliaNode1.pingNode(kademliaNode2);
        kademliaNode1.pingNode("localhost",8090);

        

    }
}
