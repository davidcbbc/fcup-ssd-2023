import AuctionMechanism.TransactionTypes.Transaction;
import AuctionMechanism.util.Item;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import kademlia.KademliaNode;
import kademlia.Util;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Main {

    public static void main(String[] args) throws InterruptedException {



        KademliaNode kademliaNode1 = Util.createNewNode();
        Thread.sleep(1000); // sleep to wait until the node start the listener

        KademliaNode kademliaNode2 = Util.createNewNode();
        Thread.sleep(1000); // sleep to wait until the node start the listener

        KademliaNode kademliaNode3 = Util.createNewNode();
        Thread.sleep(1000); // sleep to wait until the node start the listener

        kademliaNode1.getRoutingTable().update(kademliaNode2);
        kademliaNode2.getRoutingTable().update(kademliaNode1);
        //kademliaNode1.getRoutingTable().update(kademliaNode3);

        kademliaNode1.getRoutingTable().update(kademliaNode3);
        kademliaNode3.getRoutingTable().update(kademliaNode1);

        //kademliaNode3.getRoutingTable().update(kademliaNode2);
        //kademliaNode3.getRoutingTable().update(kademliaNode1);


        //System.out.println(kademliaNode1.getRoutingTable().findClosestNodes(kademliaNode3.getId(),2));

        KademliaNode kademliaNode4 = Util.createNewNode();
        Thread.sleep(1000);

        //System.out.println(kademliaNode1.getRoutingTable().getNodeCount());
        kademliaNode4.joinNetwork("localhost",8080,kademliaNode1.getId(),kademliaNode1.getWallet().getPublicKey());

        //System.out.println(kademliaNode4.getRoutingTable().getNodeCount());
        Thread.sleep(3000);
        //System.out.println(kademliaNode4.getRoutingTable().getNodeCount());
        //System.out.println(kademliaNode4.getRoutingTable());

        Item item1 = new Item("it1", "it1_desc");
        Transaction tr1 = new Transaction(kademliaNode1.getWallet().getPublicKey(), item1);
        tr1.setSignature(kademliaNode1.getWallet().signTransaction(tr1.getHash()));

        kademliaNode1.commitTransaction(tr1); // broadcast trans
        Thread.sleep(2000);

        kademliaNode2.mineBlock();


        System.out.println("node3 number of blocks -> " + kademliaNode3.getBlockchain().getChain().size());






        //kademliaNode4.store(kademliaNode2);
        //System.out.println(kademliaNode4.getRoutingTable().getNodeCount());
        //System.out.println(kademliaNode4.getRoutingTable().findClosestNodes(kademliaNode3.getId(),2));
        //System.out.println(kademliaNode1.getRoutingTable().getNodeCount());

        //kademliaNode1.pingNode(kademliaNode2);
        //kademliaNode1.pingNode("localhost",8090);

        

    }
}
