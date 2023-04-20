import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Client {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",8080)
                .usePlaintext()
                .build();

        HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(channel);

        HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
                .setFirstName("David")
                .setLastName("Capella")
                .build());

        System.out.println(helloResponse.getGreeting());

        channel.shutdown();
    }



}
