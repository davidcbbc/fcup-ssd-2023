import io.grpc.ServerBuilder;
import kademlia.KademliaNode;

public class Server {

    public static void main(String[] args) {


        /*System.out.println("[+] Starting the server");
        io.grpc.Server server = ServerBuilder
                .forPort(8080)
                .addService(new PingImpl()).build();

        try {
            server.start();
            System.out.println("[+] Server started on port 8080");
            server.awaitTermination();
        } catch (Exception e) {
            System.out.println("[-] Exception was made");
            System.out.println(e.toString());
        }*/

        int port = 8080;
        System.out.println("[+] Starting Node on port " + port);

        //KademliaNode kademliaNode = new KademliaNode("localhost",,port,null);

        /*while(true){
            try{
                Thread.sleep(5000);
                System.out.println("[+] Waiting connections ...");
            } catch (Exception e){
                System.out.println("[-] Exception was made");
                System.out.println(e.toString());
            }

        }*/


    }
}
