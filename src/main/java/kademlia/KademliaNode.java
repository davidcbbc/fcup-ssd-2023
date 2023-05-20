package kademlia;

import AuctionMechanism.Block;
import AuctionMechanism.Blockchain;
import AuctionMechanism.TransactionTypes.BidAuctionTransaction;
import AuctionMechanism.TransactionTypes.CloseAuctionTransaction;
import AuctionMechanism.TransactionTypes.CreateAuctionTransaction;
import AuctionMechanism.TransactionTypes.Transaction;
import AuctionMechanism.Wallet.Wallet;
import AuctionMechanism.util.Item;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import kademlia.grpc.GrpcHandler;
import kademlia.grpc.KademliaGrpc;
import kademlia.grpc.builders.*;

import java.io.*;
import java.math.BigInteger;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class KademliaNode implements Serializable{

    private final String address;
    private final int port;
    private final BigInteger id;
    private final GrpcHandler grpcHandler;
    private RoutingTable routingTable;
    private Blockchain blockchain;
    private List<Transaction> mempoolTransactions;
    private Wallet wallet;


    /**
     * Constructor to start a node w/ listener
     * @param address Address of the Node
     * @param id BigInteger of the Node
     * @param port Port of the Node
     */
    public KademliaNode(String address, BigInteger id, int port) {
        // set the attributes
        this.address = address;
        this.id = id;
        this.port = port;
        this.routingTable = new RoutingTable(this);
        this.blockchain = new Blockchain(3,false);
        this.wallet = new Wallet();
        this.mempoolTransactions = new ArrayList<>(); // init empty mempool
        // start the grpc handler (server) for the grpc methods (PING , FIND_NODE , etc ...)
        this.grpcHandler = new GrpcHandler(this.port,new KademliaGrpc(this));
        this.grpcHandler.start(); // start handler thread
    }


    /**
     * Constructor to create a node without GrpcHandler and Blockchain
     * @param address Address of the Node
     * @param id BigInteger of the Node
     * @param port Port of the Node
     * @param publicKey Public Key of the node
     */
    public KademliaNode(String address, BigInteger id, int port, PublicKey publicKey){
        this.address=address;
        this.id=id;
        this.port=port;
        this.wallet = new Wallet();
        this.wallet.setPublicKey(publicKey);
        this.grpcHandler=null;
    }


    /**
     * Probes a node to see if it's online.
     * @param kademliaNode node to ping
     */
    public void pingNode(KademliaNode kademliaNode){
        try{
            this.ping(kademliaNode.getAddress(), kademliaNode.getPort());
        } catch (Exception e) {
            System.out.println("[-] Could not ping the provided host due to: " + e.toString());
        }

    }

    public void pingNode(String ip, int port){
        try{
            this.ping(ip,port);
        } catch (Exception e) {
            System.out.println("[-] Could not ping the provided host due to: " + e.toString());
        }

    }

    /**
     * Probes a node to see if it's online. Uses the GRPC function
     * @param ip Ip of the node
     * @param port Port of the node
     */
    private void ping(String ip, int port) throws Exception{
        System.out.println("[+] ["+this.port+"] PINGING NODE WITH IP " + ip + " AND PORT " + port);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(ip, port)
                .usePlaintext()
                .build();

        KademliaServiceGrpc.KademliaServiceBlockingStub stub = KademliaServiceGrpc.newBlockingStub(channel);

        //sending ping
        PingResponse pingResponse = stub.ping(PingRequest.newBuilder()
                .setSender(Node.newBuilder()
                        .setAddress(this.address+":"+this.port)
                        .setId(ByteString.copyFrom(this.id.toByteArray()))
                        .setPubKey(Base64.getEncoder().encodeToString(this.wallet.getPublicKey().getEncoded())).build())
                .build());
        //pingResponse.isInitialized()
        // ping received
        System.out.println("[+] ["+this.port+"] [PING REPLY RECEIVED]");

        // sending ping
        channel.shutdown();
    }





    /**
     * Joins the network by entering the IP and port of an already known Node
     * @param ip Ip of the known Node
     * @param port Port of the known Node
     */
    public void joinNetwork(String ip, int port, BigInteger id, PublicKey publicKey){
        System.out.println("[+] ["+this.port+"] Trying to enter network with entering Node "+ip+":"+port);
        // check if node is online
        try{
            this.ping(ip,port);
        } catch (Exception e) {
            System.out.println("[-] Could not ping the provided node due to: " + e.toString());
            return;
        }
        // after checking that the node is online, add to the RoutingTable
        KademliaNode kademliaNode = new KademliaNode(ip,id,port,publicKey);
        this.routingTable.update(kademliaNode);
        System.out.println("[+] ["+this.port+"] Successfully entered the network :)");
        //System.out.println("[+] ["+this.port+"] Requesting for a blockchain copy");
        // request for a copy of the blockchain
        //this.getBlockchainCopy(kademliaNode);
    }


    public void store(KademliaNode kademliaNode, String key, Transaction transaction, Block block) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(kademliaNode.getAddress(), kademliaNode.getPort())
                .usePlaintext()
                .build();

        KademliaServiceGrpc.KademliaServiceBlockingStub stub = KademliaServiceGrpc.newBlockingStub(channel);


        if (key.equals("BLOCKCHAIN")) {
            Blockchain blockchain = this.getBlockchain();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                ObjectOutput out = new ObjectOutputStream(bos);
                out.writeObject(blockchain);
                out.flush();
            } catch (Exception e) {
                System.out.println("[-] Exception on serializing the Blockchain");
            }
            byte[] blockchainBytes = bos.toByteArray();

            try {
                StoreResponse storeResponse = stub.store(StoreRequest.newBuilder()
                        .setSender(Node.newBuilder()
                                .setAddress(this.address + ":" + this.port)
                                .setId(ByteString.copyFrom(this.id.toByteArray()))
                                .setPubKey(Base64.getEncoder().encodeToString(this.wallet.getPublicKey().getEncoded()))
                                .build())
                        .setKey(ByteString.copyFromUtf8(key))
                        .setValue(ByteString.copyFrom(blockchainBytes))
                        .build());
            } catch (Exception e) {
                System.out.println("[-] IGNORE : " + e.toString());
            }

            System.out.println("[+] [" + this.port + "] [BLOCKCHAIN STORE SENT]");
            // sending ping
            channel.shutdown();
        }

        if (key.equals("ROUTING_TABLE")) {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                ObjectOutput out = new ObjectOutputStream(bos);
                out.writeObject(this.routingTable.getBuckets());
                out.flush();
            } catch (Exception e) {
                System.out.println("[-] Exception on serializing the RoutingTable Buckets");
            }
            byte[] routingTableBytes = bos.toByteArray();

            try {
                StoreResponse storeResponse = stub.store(StoreRequest.newBuilder()
                        .setSender(Node.newBuilder()
                                .setAddress(this.address + ":" + this.port)
                                .setId(ByteString.copyFrom(this.id.toByteArray()))
                                .setPubKey(Base64.getEncoder().encodeToString(this.wallet.getPublicKey().getEncoded()))
                                .build())
                        .setKey(ByteString.copyFromUtf8(key))
                        .setValue(ByteString.copyFrom(routingTableBytes))
                        .build());
            } catch (Exception e) {
                System.out.println("[-] IGNORE : " + e.toString());
            }

            System.out.println("[+] [" + this.port + "] [ROUTING TABLE STORE SENT]");
            // sending ping
            channel.shutdown();
        }

        if (key.equals("TRANSACTION")) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                ObjectOutput out = new ObjectOutputStream(bos);
                out.writeObject(transaction);
                out.flush();
            } catch (Exception e) {
                System.out.println("[-] Exception on serializing the Transaction");
            }
            byte[] transactionBytes = bos.toByteArray();

            try {
                StoreResponse storeResponse = stub.store(StoreRequest.newBuilder()
                        .setSender(Node.newBuilder()
                                .setAddress(this.address + ":" + this.port)
                                .setId(ByteString.copyFrom(this.id.toByteArray()))
                                .setPubKey(Base64.getEncoder().encodeToString(this.wallet.getPublicKey().getEncoded()))
                                .build())
                        .setKey(ByteString.copyFromUtf8(key))
                        .setValue(ByteString.copyFrom(transactionBytes))
                        .build());
            } catch (Exception e) {
                System.out.println("[-] IGNORE : " + e.toString());
            }

            System.out.println("[+] [" + this.port + "] [TRANSACTION STORE SENT]");


        }

        if (key.equals("BLOCK")) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                ObjectOutput out = new ObjectOutputStream(bos);
                out.writeObject(block);
                out.flush();
            } catch (Exception e) {
                System.out.println("[-] Exception on serializing the Block");
            }
            byte[] blockBytes = bos.toByteArray();

            try {
                StoreResponse storeResponse = stub.store(StoreRequest.newBuilder()
                        .setSender(Node.newBuilder()
                                .setAddress(this.address + ":" + this.port)
                                .setId(ByteString.copyFrom(this.id.toByteArray()))
                                .setPubKey(Base64.getEncoder().encodeToString(this.wallet.getPublicKey().getEncoded()))
                                .build())
                        .setKey(ByteString.copyFromUtf8(key))
                        .setValue(ByteString.copyFrom(blockBytes))
                        .build());
            } catch (Exception e) {
                System.out.println("[-] IGNORE : " + e.toString());
            }

            System.out.println("[+] [" + this.port + "] [BLOCK STORE SENT]");


        }
    }


    /**
     * Saves transaction to mempool and broadcasts to all nodes
     * @param transaction Transaction to add to the local mempool and broadcasted to other nodes
     */
    public void commitTransaction(Transaction transaction){
        if(isTransactionValid(transaction)) {
            // if valid - add to mempool and broadcast it to all nodes
            this.getMempoolTransactions().add(transaction); // adds to local
            this.broadcastTransaction(transaction);
            return;
        }
        System.out.println("[-] Transaction is not valid.");
    }

    /**
     * Sends transaction to all nodes so they can add to their mempool
     * @param transaction Transaction to be sent to other nodes
     */
    private void broadcastTransaction(Transaction transaction){
        for (Bucket bucket : this.routingTable.getBuckets()){
            for (KademliaNode kademliaNode:  bucket.getNodes()) {
                System.out.println("[+] Sending transaction to node " + kademliaNode.getPort());
                this.store(kademliaNode, "TRANSACTION", transaction,null);
            }
        }
    }

    /**
     * This function broadcasts a block in the blockchain
     * @param block Block to be broadcasted
     */
    public void broadcastBlock(Block block) {
        for (Bucket bucket : this.routingTable.getBuckets()) {
            for (KademliaNode kademliaNode : bucket.getNodes()) {
                System.out.println("[+] [" + this.port + "] Sending block to node " + kademliaNode.getPort());
                this.store(kademliaNode, "BLOCK",null ,block);
            }

        }
    }


    /**
     * This function mines transactions in the mempool
     */
    public void mineBlock(){
        System.out.println("[+] [" + this.port +"] Started mining transactions");
        //Check if there are mempoolTransactions to be included in a Block
        if (this.mempoolTransactions.size()>0) {
            Block block = this.blockchain.mineBlock(this.mempoolTransactions, this.wallet);
            block.printBlock();
            if (block != null) {
                System.out.println("[+] [" + this.port + "] Adding mined block to local blockchain");
                this.blockchain.addBlock(block);
                System.out.println("[+] [" + this.port + "] Broadcasting block");
                this.broadcastBlock(block);
            }
        } else {
            System.out.println(this.toString() + "[-] Node.mineBlock nao existem transactions para fazer novo block");
        }

    }


    /**
     * Requests a blockchain copy from a KademliaNode
     * @param kademliaNode KademliaNode to request the blockchain from
     */
    public void getBlockchainCopy(KademliaNode kademliaNode){
        System.out.println("[+] ["+this.port+"] REQUESTING BLOCKCHAIN ON PORT " + kademliaNode.getPort());

        ManagedChannel channel = ManagedChannelBuilder.forAddress(kademliaNode.getAddress(), kademliaNode.getPort())
                .usePlaintext()
                .build();

        KademliaServiceGrpc.KademliaServiceBlockingStub stub = KademliaServiceGrpc.newBlockingStub(channel);


        //sending find value
        FindValueResponse findValueResponse = stub.findValue(FindValueRequest.newBuilder()
                .setSender(Node.newBuilder()
                        .setAddress(this.address+":"+this.port)
                        .setId(ByteString.copyFrom(this.id.toByteArray()))
                        .setPubKey(Base64.getEncoder().encodeToString(this.wallet.getPublicKey().getEncoded())).build())
                .setKey(ByteString.copyFromUtf8("BLOCKCHAIN")).build());

        System.out.println("asd");
        byte[] blockchainReceived = findValueResponse.getValue().toByteArray();
        try{
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(blockchainReceived));
            Blockchain blockchainClone = (Blockchain) ois.readObject();
            this.blockchain = blockchainClone;
            System.out.println("[+] ["+this.port+"] [BLOCKCHAIN RECEIVED]");
        } catch (Exception e){
            System.out.println("[-] Exception while deserializing the Blockchain , no Blockchain received");
            System.out.println(e.toString());
        }

        // sending ping
        channel.shutdown();


    }

    /**
     * Check whether two nodes are equal by comparing the ids
     * @param obj node to compare
     * @return t or f
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        KademliaNode node = (KademliaNode) obj;

        return id.equals(node.id);
    }

    /**
     * Checks if a transaction is valid before broadcasting it
     * @param transaction Transaction to check if its valid
     * @return Valid or Not
     */
    public boolean isTransactionValid(Transaction transaction) {
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

    /**
     * Check if the user has valid balance
     * @param publicKey Public Key of the user
     * @return If the user has balance or not
     */
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

    /**
     * Checks if an Auction is opened or not
     * @param auctionedItem Item for which the auction is opened
     * @return If the auction is opened or not
     */
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

    /**
     * Check the Auction highest Bids
     * @param auctionedItem Item for which we want to check the highest bid
     * @return BidAuctionTransaction
     */
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

    /**
     * Returns all transactions in the blockchain
     * @return All transactions
     */
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


    // --
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + this.id +
                ", address=" + this.address +
                '}';
    }


    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public BigInteger getId() {
        return this.id;
    }

    public RoutingTable getRoutingTable() {
        return routingTable;
    }

    public Blockchain getBlockchain() {
        return blockchain;
    }

    public List<Transaction> getMempoolTransactions() {
        return mempoolTransactions;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setBlockchain(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public void setRoutingTable(List<Bucket> buckets) {
        this.routingTable.setBuckets(buckets);
    }
}