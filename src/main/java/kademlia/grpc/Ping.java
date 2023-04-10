package kademlia.grpc;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.*;
import kademlia.Node;

public class Ping {

    private Node source;

    private Node destination;

    public Ping(Node source, Node destination) {
        this.source = source;
        this.destination = destination;
    }

    public void pingTarget() {

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(destination.getAddress().getHostAddress(), destination.getPort())
                .usePlaintext().build();


        kadServiceGrpc.kadServiceBlockingStub Kademlia = kadServiceGrpc.newBlockingStub(channel);

        try {
            grpcNode source = Util.nodeToGRPC(this.source);
            pingAnswer answer = kadService.ping(
                    pingMessage.newBuilder()
                            .setNode(source)
                            .setPubKey(ByteString.copyFrom(ownPubKey.getEncoded()))
                            .build());

            this.answer = answer.getAnswer();
            //System.out.println("PINGED");
            setResult(Util.gRPCToNode(answer.getPinged()));
        } catch (StatusRuntimeException e) {
            this.answer = false;
            //System.out.println("NOT PINGED");
        }
        channel.shutdown();
    }
}
