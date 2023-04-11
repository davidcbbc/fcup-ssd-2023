package AuctionMechanism;

import java.util.ArrayList;
import java.util.List;

public class NetworkNode {

    private Blockchain blockchain;
    private List<NetworkNode> peers;
    private List<Transaction> pendingTransactions;
    //private double stake;

    public NetworkNode(Blockchain blockchain) {
        this.blockchain = blockchain;
        this.peers = new ArrayList<>();
        this.pendingTransactions = new ArrayList<>();
        //this.peers.add(this); // add self as peer
        this.addPeer(this);
    }


    /*
    public main.Blockchain.NetworkNode(main.Blockchain.main.Blockchain blockchain, double stake) {
        this.blockchain = blockchain;
        this.peers = new ArrayList<>();
        this.pendingTransactions = new ArrayList<>();
        //this.peers.add(this); // add self as peer
        this.addPeer(this);
        this.stake = stake;
    } */

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
       /* if (transaction.isValid() && (!blockchain.getTransactions().contains(transaction))){
            blockchain.addTransaction(transaction);
        } */
        pendingTransactions.add(transaction);
    }

    public void broadcastTransaction(Transaction transaction) {
        pendingTransactions.add(transaction);
        for (NetworkNode node : peers) {
            node.receiveTransaction(transaction);
        }
        // execute the transaction PEDRO, deve ser aqui o execute? ou só qd for adicionado ao blockchain?
        //transaction.execute();
    }

    public void broadcastBlock(Block block) {
        blockchain.addBlock(block);
        for (NetworkNode node : peers) {
            node.receiveBlock(block);
        }
        pendingTransactions.clear();
    }


    public Blockchain getBlockchain() {
        return this.blockchain;
    }

    public void receiveBlock(Block block) {

        if (blockchain.isValidBlock(blockchain.getLastBlock().getHash() ,block)) {
            blockchain.addBlock(block);
            pendingTransactions.clear();
        }
        else {
            //if block is already in blockchain, remove the pendingTransactions
            if (blockchain.getChain().contains(block)) {
                pendingTransactions.clear();
            }
        }
    }

    public void addNode(NetworkNode node) {
        for (NetworkNode peer : peers) {
            if (peer != node) {
                peer.addPeer(node);
                node.addPeer(peer);
            }
        }
    }

    public void mineBlock(User miner) {
       // blockchain.mineBlock(miner);
        Block block = blockchain.mineBlock(pendingTransactions, miner);
        if (block != null) {
            broadcastBlock(block);
            // check if Users can be added to Validators list
            for (Transaction transaction : block.getTransactions()) {
                blockchain.checkAddValidator(transaction.getRecipient());
                if (!transaction.getSender().getAddress().equals("Reward")) {
                    blockchain.checkAddValidator(transaction.getSender());
                    blockchain.checkRemoveValidator(transaction.getSender());

                }
            }
        }
    }

}
