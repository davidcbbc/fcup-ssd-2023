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
        User alice = new User("alice",20);
        User bob = new User("bob", 15);
        User charlie = new User("charlie", 17);

        // create some transactions and broadcast them
        Transaction t1 = new Transaction(alice.getAddress(), bob.getAddress(), 10);
        node1.broadcastTransaction(t1);
        Transaction t2 = new Transaction(bob.getAddress(), charlie.getAddress(), 5);
        node2.broadcastTransaction(t2);

        // mine some blocks
        node1.mineBlock(alice);
        node2.mineBlock(bob);

        // check the balances of the users
        System.out.println("Alice's balance: " + alice.getBalance());
        System.out.println("Bob's balance: " + bob.getBalance());
        System.out.println("Charlie's balance: " + charlie.getBalance());
    }
}