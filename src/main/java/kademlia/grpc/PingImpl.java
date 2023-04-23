package kademlia.grpc;

import io.grpc.stub.StreamObserver;

public class PingImpl extends KademliaServiceGrpc.KademliaServiceImplBase {

    /**
     * Function that is responsible to handle the ping request
     * @param request with the Node that pinged
     * @param responseObserver callback
     */
    @Override
    public void ping(pingMessage request, StreamObserver<pingAnswer> responseObserver) {
        String id = request.getId();
        int port = request.getPort();
        String ip = request.getIp();

        System.out.println("[+] NODE PING MESSAGE: IP " + ip + ":" + Integer.toString(port) + " -> ID " + id);

        pingAnswer answer = kademlia.grpc.pingAnswer.newBuilder()
                .setId("23")
                .setPort(8080)
                .setIp("localhost")
                .setTimestamp("22:57")
                .build();

        responseObserver.onNext(answer);
        responseObserver.onCompleted();
    }
}
