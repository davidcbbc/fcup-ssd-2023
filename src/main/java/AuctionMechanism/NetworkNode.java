package AuctionMechanism;

import AuctionMechanism.TransactionTypes.*;
import AuctionMechanism.Wallet.Wallet;
import AuctionMechanism.util.Item;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class NetworkNode {

    private Blockchain blockchain;
    private List<NetworkNode> peers;
    //private List<Transaction> pendingTransactions;
    private List<Transaction> mempoolTransactions;
    //private double stake;

    //public NetworkNode(Blockchain blockchain) {
    public NetworkNode(int difficulty, boolean useProofOfStake) {
        this.blockchain = new Blockchain(difficulty, useProofOfStake);
        this.peers = new ArrayList<>();
        //this.pendingTransactions = new ArrayList<>();
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

                /*
                System.out.println("Node:chain_INICIO ");

                this.blockchain.printBlockchain();

                System.out.println("Node:chain_FIM ");

                System.out.println("Node:peers_INICIO ");

                for (NetworkNode node : peers)
                {
                    node.printNode();
                }


                System.out.println("Node:peers_FIM ");

                System.out.println("Node:mempool_INICIO ");

                for (Transaction trx : mempoolTransactions)
                {
                    trx.toString();
                }
                */

                System.out.println("Node:peers_FIM ");

                System.out.println("FIM_Node: " + this.toString());
    }

    public void receiveTransaction(Transaction transaction) {
        System.out.println("#######################RECEIVETRANSACTION#######################################################");
        /* if(!this.getPendingTransactions().contains(transaction)) {
            System.out.println(this.toString() + " receiveTransaction adicionou transaction a pending transaction");
            addPendingTransactions(transaction);
        } */
        // check if transaction is not already in the current lists
        //if (this.blockchain.isTransactionValid(transaction) && (!this.getPendingTransactions().contains(transaction)) &&  (!this.getMemPoolTransactions().contains(transaction))){
        //Validar a Transaction PEDRO
        if (transactionValid(transaction) && (!this.getMemPoolTransactions().contains(transaction))){
            System.out.println(transaction.getAuctionedItem().getName() + " -> VERIFIED TRANSACTION");
        }else {
            System.out.println(transaction.getAuctionedItem().getName() + " -> UNVERIFIED TRANSACTION");
            return;
        }

        //if (this.blockchain.isTransactionValid(transaction) &&  (!this.getMemPoolTransactions().contains(transaction))){
        if (!this.getMemPoolTransactions().contains(transaction)){
            //System.out.println(this.toString() + " receiveTransaction adicionou transaction a Mempool transactions");
            // add to mempoolTransactions
            this.addMemPoolTransactions(transaction);
            // remove from pendingTransactions
            //this.pendingTransactions.remove(transaction);
        }

        //if each node adds the same transactions? Needs to be checked -->Pending transactions are store and managed by Nodes not blockchain
        //blockchain.addPendingTransaction(transaction);
    }

    public void broadcastTransaction(Transaction transaction) {
        //System.out.println("BroadcastTransaction1");
        // check if transaction is not already in the current lists
        //if ((!this.getPendingTransactions().contains(transaction)) &&  (!this.getMemPoolTransactions().contains(transaction))) {
        //PEDRO validar Transaction antes de incluir na memPool
        if (transactionValid(transaction) ){
            System.out.println(transaction.getAuctionedItem().getName() + " -> VERIFIED TRANSACTION");
        }else {
            System.out.println(transaction.getAuctionedItem().getName() + " -> UNVERIFIED TRANSACTION");
            return;
        }
        //if (blockchain.isTransactionValid(transaction) && (!this.getMemPoolTransactions().contains(transaction))) {
        if ((!this.getMemPoolTransactions().contains(transaction))) {
            // adds Transaction to the pending list
            //System.out.println(this.toString() + "BroadcastTransaction adicionou transaction");
            addMemPoolTransactions(transaction);
            // broadcast transactions to the other nodes
            for (NetworkNode node : peers) {
                //System.out.println(this.toString() + " BroadcastTransaction fez receiveTransaction para node: " + node.toString());
                node.receiveTransaction(transaction);
            }
        }
        //System.out.println("BroadcastTransaction2");
        // execute the transaction PEDRO, deve ser aqui o execute? ou só qd for adicionado ao blockchain?
        //transaction.execute();
    }

    public void broadcastBlock(Block block) {
        //System.out.println(this.toString() + " Node.broadcastBlock para o block: " + block.toString());

        for (NetworkNode node : peers) {
            //System.out.println(this.toString() + " Node.broadcastBlock envia receive para o node " + node.toString());
            node.receiveBlock(block);
        }
        //System.out.println(this.toString() + " Node.broadcastBlock limpa mempool transactions");
        mempoolTransactions.clear();
    }


    public Blockchain getBlockchain() {
        return this.blockchain;
    }

    public void receiveBlock(Block block) {
        // check if block is not already in the blockchain and if it's valid
        //System.out.println(this.toString() + " Node.receiveBlock para o block: " + block.toString() + " vai validar se block é valido e não está na chain");
        if (!(blockchain.getChain().contains(block) && blockchain.isValidNewBlock(block, blockchain.getLastBlock()))) {
            //System.out.println(this.toString() + " Node.receiveBlock adiciona o bloco: " + block.toString());
            blockchain.addBlock(block);
            // remove transactions that were already included in the Block
            for (Transaction tr : block.getTransactions()) {
                if (mempoolTransactions.contains(tr)) {
                    mempoolTransactions.remove(tr);
                }
            }

        }
        else if (blockchain.isValidNewBlock(block, blockchain.getLastBlock())){
            //System.out.println(this.toString() + " Node.receiveBlock o bloco já estava na blockchain: " + block.toString());
            //if block is already in blockchain, remove the pendingTransactions
            if (blockchain.getChain().contains(block)) {
                System.out.println(this.toString() + " Node.receiveBlock vai remover as transações do bloco da memPool: " + block.toString());
                // remove transactions that were already included in the Block
                for (Transaction tr : block.getTransactions()) {
                    if (mempoolTransactions.contains(tr)) {
                        mempoolTransactions.remove(tr);
                    }
                }

            }
        }
        //Block is not valid, remove from blockchain PEDRO
        else if (blockchain.getChain().contains(block)) {
            //PEDRO123
            //blockchain.getChain().remove(block);
            System.out.println(this.toString() + " Node.receiveBlock received invalid Block: " + block.toString());
        }
    }


    public void mineBlock(Wallet wallet) {

        //System.out.println(this.toString() + " Node.mineBlock vai fazer mineBlock na blockchain para wallet: " + wallet.toString());
        //Check if there are mempoolTransactions to be included in a Block
        if (mempoolTransactions.size()>0) {
            Block block = blockchain.mineBlock(mempoolTransactions, wallet);
            if (block != null) {
                //System.out.println(this.toString() + " Node.mineBlock fez mineBlock com sucesso para wallet: " + wallet.toString() + " vai fazer broadcast do block");
                broadcastBlock(block);
            }
        } else {
            System.out.println(this.toString() + " Node.mineBlock nao existem transactions para fazer novo block");
        }

    }

    /*
    public void addPendingTransactions( Transaction transaction) {
        this.pendingTransactions.add(transaction);
    }

    public List<Transaction>  getPendingTransactions() {
        return this.pendingTransactions;
    }
    */
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

    public boolean transactionValid(Transaction transaction) {
        // Check if the transaction signature is valid
        if (!transaction.verifySignature()) {
            return false;
        }

        if (transaction instanceof CreateAuctionTransaction) {
            // The sender doesn't need enough balance to create an auction
            return true;
        }

        if (transaction instanceof CloseAuctionTransaction) {
            // The sender doesn't need enough balance to create an auction
            return true;
        }

        if (transaction instanceof BidAuctionTransaction) {
            BidAuctionTransaction bid = (BidAuctionTransaction) transaction;


            // Check if the Buyer has enough balance
            double senderBalance = getWalletBalance(bid.getBuyerPublicKey());
            if (senderBalance < bid.getBidAmount()) {
                return false;
            }

            // Check if the auction exists and is open
            if (!(checkAuctionOpen(bid.getAuctionedItem()))) {
                return false;
            }

            // Check if the bid is higher than the current highest bid
            BidAuctionTransaction highestBid = getHighestBid(bid.getAuctionedItem());
            if (highestBid != null && bid.getBidAmount() <= highestBid.getBidAmount()) {
                return false;
            }
        }
        return true;
    }

    public boolean checkAuctionOpen(Item auctionedItem) {
        boolean flag = false;

        List<Transaction> allTransactions = this.getAllTransactions();
        for (Transaction tr : allTransactions) {
            if (tr instanceof CreateAuctionTransaction) {
                CreateAuctionTransaction createTr = (CreateAuctionTransaction) tr;
                if (createTr.getAuctionedItem().equals(auctionedItem)) {
                    flag = true;
                }
            }
            if (tr instanceof CloseAuctionTransaction) {
                CloseAuctionTransaction closeTr = (CloseAuctionTransaction) tr;
                if (closeTr.getAuctionedItem().equals(auctionedItem)) {
                    flag = false;
                }
            }
        }
        return flag;
    }

    public BidAuctionTransaction getHighestBid(Item auctionedItem) {
        BidAuctionTransaction highestBid = null;
        List<Transaction> allTransactions = this.getAllTransactions();
        for (Transaction tr : allTransactions) {
            if (tr instanceof BidAuctionTransaction) {
                BidAuctionTransaction bidTr = (BidAuctionTransaction) tr;
                if (bidTr.getAuctionedItem().equals(auctionedItem)) {
                    if (highestBid == null || bidTr.getBidAmount() > highestBid.getBidAmount()) {
                        highestBid = bidTr;
                    }
                }
            }
        }
        return highestBid;
    }

    private float getWalletBalance(PublicKey publicKey) {
        // check closed transactions
        float balance = 100;

        //Need to get transactions from the blocks and not this list that must be the Transactions not yet in the Blockchain Blocks
        for (Transaction tr : this.getAllTransactions()) {

            if (tr instanceof CloseAuctionTransaction) {

                CloseAuctionTransaction closeTransaction = (CloseAuctionTransaction) tr;
                if ( closeTransaction.getWinnerPublicKey().equals(publicKey) ) {
                    balance = balance - closeTransaction.getWinningBid();
                }else if ( closeTransaction.getSellerPublicKey().equals(publicKey) ) {
                    balance = balance + closeTransaction.getWinningBid();
                }
            }
        }
        // check openMaxBids
        for (BidAuctionTransaction bidTr : this.blockchain.getMaxBids()) {
            if ( bidTr.getBuyerPublicKey().equals(publicKey) ) {
                balance = balance - bidTr.getBidAmount();
            }
        }

        return balance;
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> trs = new ArrayList<>();
        for (Block block : this.blockchain.getChain()) {
            //System.out.println(this.toString() + " Blockchain.getAllTransactions o bloco: " + block.toString());
            List<Transaction> tr = block.getTransactions();
            //System.out.println(this.toString() + " Blockchain.getAllTransactions o bloco: " + block.toString() + "numero de transactions:" + tr.size());
            trs.addAll(tr);
        }
        // if transactions are not in the blockchain how can we get it here?
        // trs.addAll(mempoolTransactions);
        return trs;
    }

    public void broadcastBlockchain() {
        for (NetworkNode peer : this.peers) {
            peer.receiveBlockchain(new ArrayList<>(this.blockchain.getChain()));
        }
    }

    public void receiveBlockchain(List<Block> chain) {
        // Validate the received chain
        if (!blockchain.isChainValid(chain)) {
            System.out.println("Received chain contains an invalid block. Ignoring.");
            return;
        }

        // If the chain is valid and longer, replace the current chain
        if (chain.size() > this.blockchain.getChain().size()) {
            this.blockchain.replaceChain(chain);
        }
    }

    public List<Item> getWinningAuctions(PublicKey winner){
        List<Item> items = new ArrayList<>();
        for (Transaction tr : this.getAllTransactions()) {
            if (tr instanceof CloseAuctionTransaction) {
                CloseAuctionTransaction closeTransaction = (CloseAuctionTransaction) tr;
                if ( closeTransaction.getWinnerPublicKey().equals(winner) ) {
                    items.add(closeTransaction.getAuctionedItem());
                }
            }
        }
        return items;
    }

    public List<Item> getOpenAuctions(){
        List<Item> items = new ArrayList<>();
        for (Transaction tr : this.getAllTransactions()) {
            if (tr instanceof CreateAuctionTransaction) {
                CreateAuctionTransaction createTransaction = (CreateAuctionTransaction) tr;
                items.add(createTransaction.getAuctionedItem());
            }
            if (tr instanceof CloseAuctionTransaction){
                CloseAuctionTransaction closeTransaction = (CloseAuctionTransaction) tr;
                items.remove(closeTransaction.getAuctionedItem());
            }
        }
        return items;
    }

    public void createAuction(CreateAuctionTransaction transaction) {
        if (transactionValid(transaction)) {
            this.mempoolTransactions.add(transaction);
        }
    }


}

