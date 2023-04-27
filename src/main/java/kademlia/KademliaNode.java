package kademlia;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import kademlia.grpc.GrpcHandler;
import kademlia.grpc.KademliaGrpc;
import kademlia.grpc.builders.KademliaServiceGrpc;
import kademlia.grpc.builders.Node;
import kademlia.grpc.builders.PingRequest;
import kademlia.grpc.builders.PingResponse;

import java.math.BigInteger;
import java.security.PublicKey;

public class KademliaNode {

    private final String address;
    private final int port;
    private final BigInteger id; //TODO create uid with 180 bits instead of string
    private final GrpcHandler grpcHandler;

    private final PublicKey publicKey;

    private RoutingTable routingTable;


    public KademliaNode(String address, BigInteger id, int port, PublicKey publicKey) {
        // set the attributes
        this.address = address;
        this.id = id; // TODO check if uid is already taken
        this.port = port;
        this.publicKey=publicKey;
        this.routingTable = new RoutingTable(this);
        // start the grpc handler (server) for the grpc methods (PING , FIND_NODE , etc ...)
        this.grpcHandler = new GrpcHandler(this.port,new KademliaGrpc(this));
        this.grpcHandler.start(); // start handler thread
    }


    /**
     * Probes a node to see if it's online.
     * @param address of the node to be pinged
     * @param port of the node to be pinged
     */
    public void pingNode(String address, int port){
        System.out.println("[+] PINGING NODE WITH IP " + address);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(address,port)
                .usePlaintext()
                .build();

        KademliaServiceGrpc.KademliaServiceBlockingStub stub = KademliaServiceGrpc.newBlockingStub(channel);

        // sending ping
        PingResponse pingResponse = stub.ping(PingRequest.newBuilder()
                        .setSender(Node.newBuilder()
                                .setAddress(this.address)
                                .setId(ByteString.copyFromUtf8(this.id.toString())).build())
                .build());

        // ping received
        System.out.println("[+] PING REPLY RECEIVED , NODE UID : " + pingResponse.getSender().getId());

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
}