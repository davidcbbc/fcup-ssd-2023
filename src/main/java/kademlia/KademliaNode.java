package kademlia;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import kademlia.grpc.KademliaGrpc;
import kademlia.grpc.builders.KademliaServiceGrpc;
import kademlia.grpc.builders.Node;
import kademlia.grpc.builders.PingRequest;
import kademlia.grpc.builders.PingResponse;

public class KademliaNode {

    private String address;
    private final int port = 8080;

    private String uid; //TODO create uid with 180 bits instead of string


    public KademliaNode(String address, String uid) {
        this.address = address;
        this.uid = uid; // TODO check if uid is already taken

        // Start listeners
        io.grpc.Server server = ServerBuilder
                .forPort(8080)
                .addService(new KademliaGrpc(this)).build();
    }


    public void pingNode(String address){
        KademliaServiceGrpc.KademliaServiceBlockingStub stub = this.getStubForAddress(address);

        PingResponse pingResponse = stub.ping(PingRequest.newBuilder()
                        .setSender(Node.newBuilder()
                                .setAddress(this.address)
                                .setId(ByteString.copyFromUtf8(this.uid)).build())
                .build());

        System.out.println();
    }

    //returns a grpc stub for the given address
    private KademliaServiceGrpc.KademliaServiceBlockingStub getStubForAddress(String address){
        ManagedChannel channel = ManagedChannelBuilder.forAddress(address,8080)
                .usePlaintext()
                .build();
        return KademliaServiceGrpc.newBlockingStub(channel);
    }


    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public String getUid() {
        return uid;
    }
}