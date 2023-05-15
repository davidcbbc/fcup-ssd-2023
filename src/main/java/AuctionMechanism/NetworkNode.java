package AuctionMechanism;

import AuctionMechanism.TransactionTypes.*;
import AuctionMechanism.Wallet.Wallet;

import java.util.ArrayList;
import java.util.List;

public class NetworkNode {

    private Blockchain blockchain;
    private List<NetworkNode> peers;
    private List<Transaction> pendingTransactions;
    private List<Transaction> mempoolTransactions;
    //private double stake;

    public NetworkNode(Blockchain blockchain) {
        this.blockchain = blockchain;
        this.peers = new ArrayList<>();
        this.pendingTransactions = new ArrayList<>();
        this.mempoolTransactions = new ArrayList<>();

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
        if(!this.getPendingTransactions().contains(transaction)) {
            System.out.println(this.toString() + " receiveTransaction adicionou transaction a pending transaction");
            addPendingTransactions(transaction);
        }
        // check if transaction is not already in the current lists
        if (this.blockchain.isTransactionValid(transaction) && (!this.getPendingTransactions().contains(transaction)) &&  (!this.getMemPoolTransactions().contains(transaction))){

            System.out.println(this.toString() + " receiveTransaction adicionou transaction a Mempool transactions");
            // add to mempoolTransactions
            this.addMemPoolTransactions(transaction);
            // remove from pendingTransactions
            this.pendingTransactions.remove(transaction);
        }

        //if each node adds the same transactions? Needs to be checked -->Pending transactions are store and managed by Nodes not blockchain
        //blockchain.addPendingTransaction(transaction);
    }

    public void broadcastTransaction(Transaction transaction) {
        System.out.println("BroadcastTransaction1");
        // check if transaction is not already in the current lists
        if ((!this.getPendingTransactions().contains(transaction)) &&  (!this.getMemPoolTransactions().contains(transaction))) {
            // adds Transaction to the pending list
            System.out.println(this.toString() + "BroadcastTransaction adicionou transaction");
            addPendingTransactions(transaction);
            // broadcast transactions to the other nodes
            for (NetworkNode node : peers) {
                System.out.println(this.toString() + " BroadcastTransaction fez receiveTransaction para node: " + node.toString());
                node.receiveTransaction(transaction);
            }
        }
        System.out.println("BroadcastTransaction2");
        // execute the transaction PEDRO, deve ser aqui o execute? ou só qd for adicionado ao blockchain?
        //transaction.execute();
    }

    public void broadcastBlock(Block block) {
        System.out.println(this.toString() + " Node.broadcastBlock para o block: " + block.toString());

        for (NetworkNode node : peers) {
            System.out.println(this.toString() + " Node.broadcastBlock envia receive para o node " + node.toString());
            node.receiveBlock(block);
        }
        System.out.println(this.toString() + " Node.broadcastBlock limpa pending transactions");
        pendingTransactions.clear();
    }


    public Blockchain getBlockchain() {
        return this.blockchain;
    }

    public void receiveBlock(Block block) {
        // check if block is not already in the blockchain and if it's valid
        System.out.println(this.toString() + " Node.receiveBlock para o block: " + block.toString() + " vai validar se block é valido e não está na chain");
        if (!(blockchain.getChain().contains(block) && blockchain.isValidBlock(blockchain.getLastBlock().getHash(), block))) {
            System.out.println(this.toString() + " Node.receiveBlock adiciona o bloco: " + block.toString());
            blockchain.addBlock(block);
            // remove transactions that were already included in the Block
            for (Transaction tr : block.getTransactions()) {
                if (mempoolTransactions.contains(tr)) {
                    mempoolTransactions.remove(tr);
                }
            }
            for (Transaction tr : block.getTransactions()) {
                if (pendingTransactions.contains(tr)) {
                    pendingTransactions.remove(tr);
                }
            }
        }
        else {
            System.out.println(this.toString() + " Node.receiveBlock o bloco já estava na blockchain: " + block.toString());
            //if block is already in blockchain, remove the pendingTransactions
            if (blockchain.getChain().contains(block)) {
                System.out.println(this.toString() + " Node.receiveBlock vai remover as transações do bloco da memPool: " + block.toString());
                // remove transactions that were already included in the Block
                for (Transaction tr : block.getTransactions()) {
                    if (mempoolTransactions.contains(tr)) {
                        mempoolTransactions.remove(tr);
                    }
                }
                System.out.println(this.toString() + " Node.receiveBlock vai remover as transações do bloco da pendingTransactions: " + block.toString());
                for (Transaction tr : block.getTransactions()) {
                    if (pendingTransactions.contains(tr)) {
                        pendingTransactions.remove(tr);
                    }
                }
            }
        }
    }


    public void mineBlock(Wallet wallet) {

        System.out.println(this.toString() + " Node.mineBlock vai fazer mineBlock na blockchain para wallet: " + wallet.toString());
        Block block = blockchain.mineBlock(mempoolTransactions, wallet);
        if (block != null) {
            System.out.println(this.toString() + " Node.mineBlock fez mineBlock com sucesso para wallet: " + wallet.toString() + " vai fazer broadcast do block");
            broadcastBlock(block);
        }
    }

    public void addPendingTransactions( Transaction transaction) {
        this.pendingTransactions.add(transaction);
    }

    public List<Transaction>  getPendingTransactions() {
        return this.pendingTransactions;
    }

    public void addMemPoolTransactions( Transaction transaction) {
        this.mempoolTransactions.add(transaction);
    }

    public List<Transaction>  getMemPoolTransactions() {
        return this.mempoolTransactions;
    }

    public void addNode(NetworkNode node) {
        if (!isNodeConnected(node)) {
            peers.add(node);
            node.addNode(this); // Establish the reciprocal connection
            broadcastNewNode(node);
        }
    }

    private void broadcastNewNode(NetworkNode newNode) {
        List<NetworkNode> peersCopy = new ArrayList<>(peers);
        for (NetworkNode connectedNode : peersCopy) {
            connectedNode.receiveNode(newNode);
        }
    }

    public void receiveNode(NetworkNode newNode) {
        if (newNode != this && !isNodeConnected(newNode)) {
            peers.add(newNode);
            newNode.addNode(this); // Establish the reciprocal connection
        }
    }

    private boolean isNodeConnected(NetworkNode node) {
        for (NetworkNode connectedNode : peers) {
            if (connectedNode == node) {
                return true;
            }
        }
        return false;
    }

    public List<NetworkNode> getPeers() {
        return peers;
    }
}
