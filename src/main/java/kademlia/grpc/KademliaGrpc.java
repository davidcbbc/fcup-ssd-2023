package kademlia.grpc;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import kademlia.KademliaNode;
import kademlia.grpc.builders.*;

/**
 * Class that is responsible for implementing all GRPC functions.
 */
public class KademliaGrpc extends KademliaServiceGrpc.KademliaServiceImplBase{

    private final KademliaNode kademliaNode; // source node

    public KademliaGrpc(KademliaNode kademliaNode) {
        this.kademliaNode = kademliaNode;
    }

    /**
     * This function handles the PING requests.
     * @param request with the Node that pinged
     * @param responseObserver callback
     */
    @Override
    public void ping(PingRequest request, StreamObserver<PingResponse> responseObserver) {
        Node sourceNode = request.getSender();


        System.out.println("[+] [NODE PING MESSAGE] | IP: " + sourceNode.getAddress() + " | ID: " + sourceNode.getId());

        PingResponse response = PingResponse.newBuilder()
                .setSender(Node.newBuilder()
                        .setAddress(this.kademliaNode.getAddress())
                        .setId(ByteString.copyFromUtf8(this.kademliaNode.getId().toString()))
                        .setAddressBytes(ByteString.copyFromUtf8("asd"))).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    /**
     * Instructs a node to store a key-value pair
     * This function handles the STORE requests.
     * @param request StoreRequest
     * @param responseObserver StreamObserver
     */
    @Override
    public void store(StoreRequest request, StreamObserver<StoreResponse> responseObserver) {
        super.store(request, responseObserver);
    }

    /**
     * Returns information about the k nodes closest to the target id
     * @param request FindNodeRequest
     * @param responseObserver StreamObserver
     */
    @Override
    public void findNode(FindNodeRequest request, StreamObserver<FindNodeResponse> responseObserver) {
        super.findNode(request, responseObserver);
    }


    /**
     * Similar to the FIND_NODE RPC, but if the recipient has received a STORE
     * for the given key, it just returns the stored value.
     * @param request FindValueRequest
     * @param responseObserver StreamObserver
     */
    @Override
    public void findValue(FindValueRequest request, StreamObserver<FindValueResponse> responseObserver) {
        super.findValue(request, responseObserver);
    }
}
