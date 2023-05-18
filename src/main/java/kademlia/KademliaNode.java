package kademlia;

import AuctionMechanism.Block;
import AuctionMechanism.Blockchain;
import AuctionMechanism.TransactionTypes.Transaction;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import kademlia.grpc.GrpcHandler;
import kademlia.grpc.KademliaGrpc;
import kademlia.grpc.builders.KademliaServiceGrpc;
import kademlia.grpc.builders.Node;
import kademlia.grpc.builders.PingRequest;
import kademlia.grpc.builders.PingResponse;

import java.math.BigInteger;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class KademliaNode {

    private final String address;
    private final int port;
    private final BigInteger id;
    private final GrpcHandler grpcHandler;
    private final PublicKey publicKey;
    private RoutingTable routingTable;
    private Blockchain blockchain;
    private List<Transaction> mempoolTransactions;


    public KademliaNode(String address, BigInteger id, int port, PublicKey publicKey) {
        // set the attributes
        this.address = address;
        this.id = id; // TODO check if uid is already taken
        this.port = port;
        this.publicKey=publicKey;
        this.routingTable = new RoutingTable(this);
        this.blockchain = new Blockchain(10,true);
        this.mempoolTransactions = new ArrayList<>(); // init empty mempool
        // start the grpc handler (server) for the grpc methods (PING , FIND_NODE , etc ...)
        this.grpcHandler = new GrpcHandler(this.port,new KademliaGrpc(this));
        this.grpcHandler.start(); // start handler thread
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
                        .setAddress(this.address)
                        .setId(ByteString.copyFromUtf8(this.id.toString())).build())
                .build());
        //pingResponse.isInitialized()
        // ping received
        System.out.println("[+] ["+this.port+"] PING REPLY RECEIVED , NODE UID : " + pingResponse.getSender().getId());

        // sending ping
        channel.shutdown();
    }



    /**
     * This function broadcasts a block in the blockchain
     * @param block Block to be broadcasted
     */
    public void broadcastBlock(Block block){

        // TODO
    }

    /**
     * Joins the network by entering the IP and port of an already known Node
     * @param ip Ip of the known Node
     * @param port Port of the known Node
     */
    public void joinNetwork(String ip, int port){
        System.out.println("[+] Trying to enter network with entering Node "+ip+":"+port);
        // check if node is online
        try{
            this.ping(ip,port);
        } catch (Exception e) {
            System.out.println("[-] Could not ping the provided node due to: " + e.toString());
            return;
        }
        // after checking that the node is online, add to the RoutingTable
        //this.routingTable.



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
}