import java.util.List;
import java.util.ArrayList;

public class NetworkNode {
    private Blockchain blockchain;
    private List<NetworkNode> peers;
    private List<Transaction> pendingTransactions;

    public NetworkNode(Blockchain blockchain, List<NetworkNode> peers) {
        this.blockchain = blockchain;
        this.peers = peers;
        this.pendingTransactions = new ArrayList<>();
    }

    public void addNode(NetworkNode node) {
        peers.add(node);
    }

    public void broadcastTransaction(Transaction transaction) {
        pendingTransactions.add(transaction);
        for (NetworkNode node : peers) {
            node.receiveTransaction(transaction);
        }
    }

    public void receiveTransaction(Transaction transaction) {
        pendingTransactions.add(transaction);
    }

    public void broadcastBlock(Block block) {
        blockchain.addBlock(block);
        for (NetworkNode node : peers) {
            node.receiveBlock(block);
        }
        pendingTransactions.clear();
    }

    public void receiveBlock(Block block) {
        if (blockchain.isValidBlock(block)) {
            blockchain.addBlock(block);
            pendingTransactions.clear();
        }
    }

    public void mineBlock(User miner) {
        Block block = blockchain.mineBlock(pendingTransactions, miner);
        if (block != null) {
            broadcastBlock(block);
        }
    }
}

/*
    public void receiveTransaction(Transaction transaction) {
        blockchain.addTransaction(transaction);
        broadcastTransaction(transaction);
    }

    public void receiveBlock(Block block) {
        if (blockchain.isValidBlock(block.getPreviousBlockHash())) {
            blockchain.addBlock(block);
            broadcastBlock(block);
        }
    }
}
*/