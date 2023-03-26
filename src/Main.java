import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        // create a network of nodes
        List<NetworkNode> nodes = new ArrayList<>();
        Blockchain blockchain = new Blockchain(3); // PoW difficulty level of 3
        NetworkNode node1 = new NetworkNode(blockchain, nodes);
        NetworkNode node2 = new NetworkNode(blockchain, nodes);
        nodes.add(node1);
        nodes.add(node2);

        // create some users
        User alice = new User("alice");
        User bob = new User("bob");
        User charlie = new User("charlie");

        // create some transactions and broadcast them
        Transaction t1 = new Transaction(alice.getAddress(), bob.getAddress(), 10);
        node1.broadcastTransaction(t1);
        Transaction t2 = new Transaction(bob.getAddress(), charlie.getAddress(), 5);
        node2.broadcastTransaction(t2);

        // mine some blocks
        node1.mineBlock(new User("miner1"));
        node2.mineBlock(new User("miner2"));

        // check the balances of the users
        System.out.println("Alice's balance: " + alice.getBalance());
        System.out.println("Bob's balance: " + bob.getBalance());
        System.out.println("Charlie's balance: " + charlie.getBalance());
    }
}