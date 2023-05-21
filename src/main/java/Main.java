import AuctionMechanism.TransactionTypes.BidAuctionTransaction;
import AuctionMechanism.TransactionTypes.CloseAuctionTransaction;
import AuctionMechanism.TransactionTypes.CreateAuctionTransaction;
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
        kademliaNode2.getRoutingTable().update(kademliaNode3);
        //kademliaNode1.getRoutingTable().update(kademliaNode3);

        kademliaNode1.getRoutingTable().update(kademliaNode3);
        kademliaNode3.getRoutingTable().update(kademliaNode1);

        //kademliaNode3.getRoutingTable().update(kademliaNode2);
        //kademliaNode3.getRoutingTable().update(kademliaNode1);


        //System.out.println(kademliaNode1.getRoutingTable().findClosestNodes(kademliaNode3.getId(),2));

        KademliaNode kademliaNode4 = Util.createNewNode();
        kademliaNode2.getRoutingTable().update(kademliaNode4);
        Thread.sleep(1000);

        //System.out.println(kademliaNode1.getRoutingTable().getNodeCount());
        kademliaNode4.joinNetwork("localhost",8080,kademliaNode1.getId(),kademliaNode1.getWallet().getPublicKey());

        //System.out.println(kademliaNode4.getRoutingTable().getNodeCount());
        Thread.sleep(3000);
        //System.out.println(kademliaNode4.getRoutingTable().getNodeCount());
        //System.out.println(kademliaNode4.getRoutingTable());

        Item item1 = new Item("it1", "it1_desc");
        Transaction tr1 = new CreateAuctionTransaction(kademliaNode1.getWallet().getPublicKey(), item1,100);
        tr1.setSignature(kademliaNode1.getWallet().signTransaction(tr1.getHash()));

        Item item2 = new Item("it2", "it2_desc");
        Transaction tr2 = new CreateAuctionTransaction(kademliaNode1.getWallet().getPublicKey(), item2,200);
        tr2.setSignature(kademliaNode1.getWallet().signTransaction(tr2.getHash()));

        kademliaNode1.commitTransaction(tr1); // broadcast trans
        kademliaNode1.commitTransaction(tr2); // broadcast trans
        Thread.sleep(2000);

        kademliaNode1.mineBlock();

        Item item3 = new Item("it3", "it3_desc");
        Transaction tr3 = new CreateAuctionTransaction(kademliaNode1.getWallet().getPublicKey(), item3,10);
        tr3.setSignature(kademliaNode1.getWallet().signTransaction(tr3.getHash()));
        Transaction tr4 = new BidAuctionTransaction(kademliaNode1.getWallet().getPublicKey(),kademliaNode2.getWallet().getPublicKey(), item3,20);
        tr4.setSignature(kademliaNode2.getWallet().signTransaction(tr4.getHash()));
        //Transaction tr5 = new CloseAuctionTransaction(kademliaNode1.getWallet().getPublicKey(), item3,300);
        //tr5.setSignature(kademliaNode1.getWallet().signTransaction(tr5.getHash()));



        kademliaNode1.commitTransaction(tr3);
        Thread.sleep(2000);
        kademliaNode2.mineBlock();
        Thread.sleep(2000);
        kademliaNode1.commitTransaction(tr4);// broadcast trans

        kademliaNode2.mineBlock();
        Thread.sleep(2000);

        BidAuctionTransaction winner = kademliaNode1.getHighestBid(item3);
        Transaction tr5 = new CloseAuctionTransaction(kademliaNode1.getWallet().getPublicKey(), item3,winner.getBuyerPublicKey(),winner.getBidAmount(),null);
        tr5.setSignature(kademliaNode1.getWallet().signTransaction(tr5.getHash()));
        kademliaNode1.commitTransaction(tr5);// broadcast trans
        kademliaNode2.mineBlock();
        Thread.sleep(5000);
        System.out.println("node1 number of blocks -> " + kademliaNode1.getBlockchain().getChain().size() + " Second block transaction number:" + kademliaNode1.getBlockchain().getChain().get(1).getTransactions().size() + " third block transaction number:" + kademliaNode1.getBlockchain().getChain().get(2).getTransactions().size() + "mempool size:" + kademliaNode1.getMempoolTransactions().size());
        System.out.println("node2 number of blocks -> " + kademliaNode2.getBlockchain().getChain().size() + " Second block transaction number:" + kademliaNode2.getBlockchain().getChain().get(1).getTransactions().size() + " third block transaction number:" + kademliaNode2.getBlockchain().getChain().get(2).getTransactions().size() + "mempool size:" + kademliaNode2.getMempoolTransactions().size());
        System.out.println("node3 number of blocks -> " + kademliaNode3.getBlockchain().getChain().size() + " Second block transaction number:" + kademliaNode3.getBlockchain().getChain().get(1).getTransactions().size() + " third block transaction number:" + kademliaNode3.getBlockchain().getChain().get(2).getTransactions().size() + "mempool size:" + kademliaNode3.getMempoolTransactions().size());
        System.out.println("node4 number of blocks -> " + kademliaNode4.getBlockchain().getChain().size() + " Second block transaction number:" + kademliaNode4.getBlockchain().getChain().get(1).getTransactions().size() + " third block transaction number:" + kademliaNode4.getBlockchain().getChain().get(2).getTransactions().size() + "mempool size:" + kademliaNode4.getMempoolTransactions().size());

        System.out.println("Highest: "+ kademliaNode1.getHighestBid(item3));

        for (Transaction i : kademliaNode1.getOpenAuctions()) {
            System.out.println(i.getAuctionedItem().getName());
        }



        //kademliaNode4.store(kademliaNode2);
        //System.out.println(kademliaNode4.getRoutingTable().getNodeCount());
        //System.out.println(kademliaNode4.getRoutingTable().findClosestNodes(kademliaNode3.getId(),2));
        //System.out.println(kademliaNode1.getRoutingTable().getNodeCount());

        //kademliaNode1.pingNode(kademliaNode2);
        //kademliaNode1.pingNode("localhost",8090);



    }
}
