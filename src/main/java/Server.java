import io.grpc.ServerBuilder;

public class Server {

    public static void main(String[] args) {


        System.out.println("[+] Starting the server");
        io.grpc.Server server = ServerBuilder
                .forPort(8080)
                .addService(new HelloServiceImpl()).build();

        try {
            server.start();
            System.out.println("[+] Server started on port 8080");
            server.awaitTermination();
        } catch (Exception e) {
            System.out.println("[-] Exception was made");
            System.out.println(e.toString());
        }


    }
}
