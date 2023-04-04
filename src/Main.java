import java.util.ArrayList;
import java.util.List;

public class Main {


    /*
    public static void main(String[] args) {
        // create a network of nodes
        List<NetworkNode> nodes = new ArrayList<>();
        Blockchain blockchain = new Blockchain(3); // PoW difficulty level of 3
        blockchain.printBlockchain();
        System.out.println("111111111111-Antes dos Nodes-111111111111");
        NetworkNode node1 = new NetworkNode(blockchain);
        System.out.println("222222222222-Depois de criar o Node1-222222222222");
        node1.printNode();
        blockchain.printBlockchain();
        NetworkNode node2 = new NetworkNode(blockchain);
        System.out.println("333333333333-Depois de criar o Node2-333333333333");
        node2.printNode();
        blockchain.printBlockchain();
        nodes.add(node1);
        System.out.println("444444444444-Depois de adicionar o Node1 aos nodes-444444444444");
        node1.printNode();
        node2.printNode();
        blockchain.printBlockchain();
        nodes.add(node2);
        System.out.println("555555555555-Depois de adicionar o Node2 aos nodes-555555555555");
        node1.printNode();
        node2.printNode();
        blockchain.printBlockchain();

        // create some users
        User alice = new User("alice",20);
        User bob = new User("bob", 15);
        User charlie = new User("charlie", 17);

        // create some transactions and broadcast them
        Transaction t1 = new Transaction(alice.getAddress(), bob.getAddress(), 10);
        System.out.println("6666666666666-Depois de criar a Transaction1-6666666666666");
        t1.printTransaction();
        blockchain.printBlockchain();

        node1.broadcastTransaction(t1);
        System.out.println("77777777777777-Depois do broadcast da Transaction1 no Node1-77777777777777");
        node1.printNode();
        node2.printNode();
        t1.printTransaction();
        blockchain.printBlockchain();

        Transaction t2 = new Transaction(bob.getAddress(), charlie.getAddress(), 5);
        System.out.println("88888888888888-Depois de criar a Transaction2-88888888888888");
        t2.printTransaction();
        node2.broadcastTransaction(t2);
        blockchain.printBlockchain();
        System.out.println("99999999999999-Depois do broadcast da Transaction2 no Node2-99999999999999");
        node1.printNode();
        node2.printNode();
        t2.printTransaction();
        blockchain.printBlockchain();

        // mine some blocks
        node1.mineBlock(alice);
        System.out.println("1010101010101010-Depois do mineBlock do Node1-1010101010101010");
        node1.printNode();
        node2.printNode();
        blockchain.printBlockchain();

        node2.mineBlock(bob);
        System.out.println("11 11 11 11 11 11-Depois do mineBlock do Node2-11 11 11 11 11 11");
        node1.printNode();
        node2.printNode();
        blockchain.printBlockchain();


        // check the balances of the users
        System.out.println("Alice's balance: " + alice.getBalance());
        System.out.println("Bob's balance: " + bob.getBalance());
        System.out.println("Charlie's balance: " + charlie.getBalance());

    } */

    public static void main(String[] args) {
        // create a blockchain with a genesis block
        Blockchain blockchain = new Blockchain(3);
       // Block genesisBlock = new Block(new ArrayList<>(), 0,"0");
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
        User alice = new User("Alice",20);
        User bob = new User("Bob",10);
        User charlie = new User("Charlie",10);
        Transaction tx1 = new Transaction(alice, bob, 10);
        Transaction tx2 = new Transaction(alice, charlie, 5);
        Transaction tx3 = new Transaction(bob, charlie, 2);

        // broadcast the transactions to the network
        node1.broadcastTransaction(tx1);
      // 1  node1.broadcastTransaction(tx2);

        node3.mineBlock(alice);

        node1.broadcastTransaction(tx3);

        // mine some blocks containing the transactions
        node1.mineBlock(alice);

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

        // print the current state of each User
        System.out.println("alice balance: " + alice.getBalance());
        System.out.println("bob balance: " + bob.getBalance());
        System.out.println("charlie balance: " + charlie.getBalance());
    }

}