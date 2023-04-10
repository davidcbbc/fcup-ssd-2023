package Blockchain;

import java.util.Map;

public class Main {

    public static void main(String[] args) {

        boolean useProofOfStake = true;
        // create a blockchain with a genesis block
        Blockchain blockchain = new Blockchain(3, useProofOfStake);
       // Blockchain.Blockchain.Block genesisBlock = new Blockchain.Blockchain.Block(new ArrayList<>(), 0,"0");
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

        // print the current state of each Blockchain.User
        System.out.println("alice balance: " + alice.getBalance());
        System.out.println("bob balance: " + bob.getBalance());
        System.out.println("charlie balance: " + charlie.getBalance());


        for (Map.Entry<User, Double> validator : blockchain.getValidators().entrySet()) {
            System.out.println("Validator: " + validator.getKey().getAddress() + " : " + validator.getValue());

        }
    }

}