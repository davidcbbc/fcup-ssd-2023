import java.util.ArrayList;
import java.util.List;

public class NetworkNode {

    private Blockchain blockchain;
    private List<NetworkNode> peers;

    public NetworkNode(Blockchain blockchain) {
        this.blockchain = blockchain;
        this.peers = new ArrayList<>();
        //this.peers.add(this); // add self as peer
        this.addPeer(this);
    }

    public void addPeer(NetworkNode node) {
        /* if (!peers.contains(node)) {
            peers.add(node);
            node.addPeer(this);
        } */
        if (node != this) {
            peers.add(node);
        }
    }

    public void printNode() {
                System.out.println("INICIO_Node: " + this.toString());

                System.out.println("Node:chain_INICIO ");

                this.blockchain.printBlockchain();

                System.out.println("Node:chain_FIM ");

                System.out.println("Node:peers_INICIO ");

                for (NetworkNode node : peers)
                {
                    node.printNode();
                }
                System.out.println("Node:peers_FIM ");

                System.out.println("FIM_Node: " + this.toString());
    }

    public void receiveTransaction(Transaction transaction) {
       // previous blockchain.addTransaction(transaction);
        if (transaction.isValid() && (!blockchain.getTransactions().contains(transaction))){
            blockchain.addTransaction(transaction);
        }
    }

    public void broadcastTransaction(Transaction transaction) {
        blockchain.addTransaction(transaction);
        for (NetworkNode node : peers) {
            node.receiveTransaction(transaction);
        }
        // execute the transaction
        transaction.execute();
    }

    public void broadcastBlock(Block block) {
        blockchain.addBlock(block);
        for (NetworkNode node : peers) {
            node.receiveBlock(block);
        }
    }


    public Blockchain getBlockchain() {
        return this.blockchain;
    }

    public void receiveBlock(Block block) {

        if (blockchain.isValidBlock(blockchain.getLastBlock().getHash() ,block)) {
            blockchain.addBlock(block);
        }
    }


/*
    public void receiveBlock(Block block) {
        if (blockchain.isValidBlock(block)) {
            blockchain.addBlock(block);
        } else if (blockchain.isChainLengthValid(blockchain.getBlocks())) {
            // if the received block is part of a longer chain, request the longer chain from peers
            broadcastRequestBlockchain();
        }
    } */

    public void addNode(NetworkNode node) {
        for (NetworkNode peer : peers) {
            if (peer != node) {
                peer.addPeer(node);
                node.addPeer(peer);
            }
        }
    }

    public void mineBlock(User miner) {
        blockchain.mineBlock(miner);
        broadcastBlock(blockchain.getLastBlock());
    }

    public void broadcastRequestBlockchain() {
        for (NetworkNode peer : peers) {
            peer.receiveRequestBlockchain();
        }
    }

    public void receiveRequestBlockchain() {
        broadcastBlockchain();
    }

    public void broadcastBlockchain() {
        for (NetworkNode peer : peers) {
            if (peer != this) {
                peer.receiveBlockchain(blockchain.getChain());
            }
        }
    }

    public void receiveBlockchain(List<Block> newChain) {
        if (blockchain.isChainValid(newChain)) {
            blockchain.replaceChain(newChain);
        }
    }
}
