package kademlia.grpc;

import io.grpc.stub.StreamObserver;

public class PingImpl extends KademliaServiceGrpc.KademliaServiceImplBase {

    /**
     * Function that is responsible to handle the ping request
     * @param request
     * @param responseObserver
     */
    @Override
    public void ping(pingMessage request, StreamObserver<pingAnswer> responseObserver) {

    }
}
