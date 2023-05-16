package AuctionMechanism;

import AuctionMechanism.TransactionTypes.CreateAuctionTransaction;
import AuctionMechanism.TransactionTypes.Transaction;
import AuctionMechanism.Wallet.Wallet;
import AuctionMechanism.util.Item;
import org.bouncycastle.operator.OperatorCreationException;

import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.security.*;

public class Main {

    public static void main(String[] args) throws GeneralSecurityException, IOException, OperatorCreationException {

        boolean useProofOfStake = false;
        // create a blockchain with a genesis block
        //Blockchain blockchain = new Blockchain(3, useProofOfStake);
        // main.Blockchain.main.Blockchain.Block genesisBlock = new main.Blockchain.main.Blockchain.Block(new ArrayList<>(), 0,"0");
        //blockchain.addBlock(genesisBlock);

        // create three network nodes with the blockchain
        NetworkNode node1 = new NetworkNode(3, useProofOfStake);
        NetworkNode node2 = new NetworkNode(3, useProofOfStake);
        NetworkNode node3 = new NetworkNode(3, useProofOfStake);

        // add each node to the other nodes' peer lists
        node1.addNode(node2);
        node2.addNode(node3);

        /*
        System.out.println("Node:" + node1.toString() + " has the following nodes:");
        for (NetworkNode node : node1.getPeers()) {
            System.out.println("Node:" + node.toString());

        }

        System.out.println("Node:" + node2.toString() + " has the following nodes:");
        for (NetworkNode node : node2.getPeers()) {
            System.out.println("Node:" + node.toString());

        }

        System.out.println("Node:" + node3.toString() + " has the following nodes:");
        for (NetworkNode node : node3.getPeers()) {
            System.out.println("Node:" + node.toString());

        } */
        Wallet wallet1 = new Wallet();
        Wallet wallet2 = new Wallet();

        Item item1 = new Item("it1", "it1_desc");
        Item item2 = new Item("it2", "it2_desc");
        Item item3 = new Item("it3", "it3_desc");
        Item item4 = new Item("it4", "it4_desc");
        Item item5 = new Item("it5", "it5_desc");

        Transaction tr1 = new Transaction(wallet1.getPublicKey(), item1);
        tr1.setSignature(wallet1.signTransaction(tr1.getHash()));

        Transaction tr2 = new Transaction(wallet2.getPublicKey(), item2);
        tr2.setSignature(wallet2.signTransaction(tr2.getHash()));

        Transaction tr3 = new Transaction(wallet1.getPublicKey(), item3);
        tr3.setSignature(wallet1.signTransaction(tr3.getHash()));

        Transaction tr4 = new Transaction(wallet2.getPublicKey(), item4);
        tr4.setSignature(wallet2.signTransaction(tr4.getHash()));

        Transaction tr5 = new Transaction(wallet1.getPublicKey(), item5);
        tr5.setSignature(wallet1.signTransaction(tr5.getHash()));

        node2.mineBlock(wallet1);
        node1.broadcastTransaction(tr1);
        //node2.broadcastTransaction(tr4);
        node1.broadcastTransaction(tr1);
        node3.broadcastTransaction(tr3);
        //node3.broadcastTransaction(tr2);


        /*
        System.out.println("##deppois da 1 e 3###");
        System.out.println("Node:" + node1.toString() + " has this number of mempool connections: " + node1.getMemPoolTransactions().size());
        System.out.println("Node:" + node2.toString() + " has this number of mempool connections: " + node2.getMemPoolTransactions().size());
        System.out.println("Node:" + node3.toString() + " has this number of mempool connections: " + node3.getMemPoolTransactions().size());

         */
        node2.mineBlock(wallet2);

        // System.out.println("##depois do mineblock ###");

        node3.mineBlock(wallet1);
        node1.mineBlock(wallet2);


        node3.broadcastTransaction(tr2);
        node3.mineBlock(wallet2);
        node2.broadcastTransaction(tr4);

        //System.out.println("##deppois da 2###");


        node1.mineBlock(wallet1);


        node1.broadcastTransaction(tr5);



        node2.mineBlock(wallet1); // 7

        System.out.println("*******************************************************************************************");
        System.out.println("Node:" + node1.toString() + " has this number of blocks in the blockchain: " + node1.getBlockchain().getChain().size());
        System.out.println("Node:" + node1.toString() + " has this number of mempoolTransactions in the blockchain: " + node1.getMemPoolTransactions().size());

        node1.getBlockchain().printBlockchain();

        System.out.println("*******************************************************************************************");
        System.out.println("Node:" + node2.toString() + " has this number of blocks in the blockchain: " + node2.getBlockchain().getChain().size());
        System.out.println("Node:" + node2.toString() + " has this number of mempoolTransactions in the blockchain: " + node2.getMemPoolTransactions().size());

        node2.getBlockchain().printBlockchain();

        System.out.println("*******************************************************************************************");
        System.out.println("Node:" + node3.toString() + " has this number of blocks in the blockchain: " + node3.getBlockchain().getChain().size());
        System.out.println("Node:" + node3.toString() + " has this number of mempoolTransactions in the blockchain: " + node3.getMemPoolTransactions().size());

        node3.getBlockchain().printBlockchain();

        /*
        Scanner in= new Scanner(System.in);
        Wallet wallet=null;

        int keyChoice = -1;
        System.out.println("1 - Create new Key Pair\n2 - Import Key Pair\n");
        System.out.println("Please Choose an option between 1 and 2");
        keyChoice = in.nextInt();
        while(keyChoice > 2 || keyChoice < 1){
            System.out.println("Please Choose an option between 1 and 5");
            keyChoice = in.nextInt();
        }

        System.out.println("Please Enter the Path of the Key Pair");
        in.nextLine();
        String path = in.nextLine();

        System.out.println("Please Enter the Password of the Key Pair");

        String pass = in.nextLine();

        switch (keyChoice){
            case 1 :
                wallet = new Wallet();
                try {
                    wallet.saveToFile(path, pass);
                    System.out.println("Alice's wallet saved to " + path);
                } catch (Exception e) {
                    System.err.println("Error saving wallet: " + e.getMessage());
                }
                break;
            case 2 :
                try {
                    wallet = Wallet.loadFromFile(path, pass);
                    System.out.println("Loaded wallet from " + path);
                } catch (Exception e) {
                    System.err.println("Error loading wallet: " + e.getMessage());
                }
                break;
            default : break;
        }



        // IMPORT OR CREATE PAIR OF PUBLIC AND PRIVATE KEY FOR AUTHENTICATION
        int choice = -1;
        while (choice != 5){


            System.out.println("1 - Create Auction\n2 - See Open Auctions\n3 - Make a Bid\n4 - See Winning Bids\n5 - Exit\n");
            System.out.println("Please Choose an option between 1 and 5");
            choice = in.nextInt();
            while (choice < 1 || choice > 5){
                System.out.println("Please Choose an option between 1 and 5");
                choice = in.nextInt();
            }

            switch (choice){
                case 1 :
                    // Call Create Auction Function
                    System.out.println("Provide item name:" );
                    String itemName = in.nextLine();
                    System.out.println("Provide item description:" );
                    String itemDesc = in.nextLine();
                    System.out.println("Provide minimum bid:" );
                    int minimumBid = in.nextInt();
                    Item item = new Item(itemName, itemDesc);
                    CreateAuctionTransaction tx = new CreateAuctionTransaction(wallet.getPublicKey(), item, minimumBid);
                    node1.broadcastTransaction(tx);
                    node2.mineBlock(wallet);

                    System.out.println("Created Auction for Item:" + itemName + " with description:" + itemDesc + " for user:" + wallet.getPublicKey().toString());
                    break;
                case 2 :
                    // Call See Open Auction Function


                    break;
                case 3 :
                    // Call Make Bid Function
                    break;
                case 4 : 
                    // Call See Winning Bids Function
                    break;
                case 5 :
                    System.out.println("Exiting...");
                    break;
                default :
                    System.out.println("Choice default");
                    break;

            }
        }


    */




        /*
        boolean useProofOfStake = true;
        // create a blockchain with a genesis block
        Blockchain blockchain = new Blockchain(3, useProofOfStake);
       // main.Blockchain.main.Blockchain.Block genesisBlock = new main.Blockchain.main.Blockchain.Block(new ArrayList<>(), 0,"0");
        //blockchain.addBlock(genesisBlock);

        // create three network nodes with the blockchain
        NetworkNode node1 = new NetworkNode(blockchain);
        NetworkNode node2 = new NetworkNode(blockchain);
        NetworkNode node3 = new NetworkNode(blockchain);

        // add each node to the other nodes' peer lists
        node1.addPeer(node2);
        node1.addPeer(node3);
        node2.addPeer(node1);
        node2.addPeer(node3);
        node3.addPeer(node1);
        node3.addPeer(node2);

        // create some users and add transactions
        User alice = new User("Alice",2990);
        blockchain.addValidator(alice);
        User bob = new User("Bob",10);
        User charlie = new User("Charlie",10);
        Transaction tx1 = new Transaction(alice, bob, 1000);
        Transaction tx2 = new Transaction(alice, charlie, 5);
        Transaction tx3 = new Transaction(bob, charlie, 2);

        // broadcast the transactions to the network
        node1.broadcastTransaction(tx1);
      // 1  node1.broadcastTransaction(tx2);

        node3.mineBlock(alice);

        node1.broadcastTransaction(tx3);

        // mine some blocks containing the transactions
        node1.mineBlock(bob);

        // 1
        //node1.broadcastTransaction(tx2);
        //node2.mineBlock(bob);

        // print the current state of the blockchain for each node
        System.out.println("Node 1 blockchain:");
        node1.getBlockchain().printBlockchain();

        // 1
        node1.broadcastTransaction(tx2);
        node2.mineBlock(bob);

        System.out.println("Node 2 blockchain:");
        node2.getBlockchain().printBlockchain();
        //System.out.println("Node 3 blockchain:");
        //node3.getBlockchain().printBlockchain();

        // print the current state of each main.Blockchain.User
        System.out.println("alice balance: " + alice.getBalance());
        System.out.println("bob balance: " + bob.getBalance());
        System.out.println("charlie balance: " + charlie.getBalance());


        for (Map.Entry<User, Double> validator : blockchain.getValidators().entrySet()) {
            System.out.println("Validator: " + validator.getKey().getAddress() + " : " + validator.getValue());

        }
    */
    }



}