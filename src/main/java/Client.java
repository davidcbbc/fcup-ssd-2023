import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Client {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",8080)
                .usePlaintext()
                .build();


        KademliaServiceGrpc.KademliaServiceBlockingStub stub = KademliaServiceGrpc.newBlockingStub(channel);


        pingAnswer answer = stub.ping(pingMessage.newBuilder()
                .setId("1")
                .setIp("localhost")
                .setPort(8080).build());

        System.out.println("[+] NODE PING REPLY: IP " + answer.getIp() + ":" + Integer.toString(answer.getPort()) + " -> ID " + answer.getId());

        channel.shutdown();
    }



}
