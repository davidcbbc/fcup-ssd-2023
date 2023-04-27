import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {


        BigInteger uid = new BigInteger("1234567890123456789012345678901234567890",16);

        System.out.println(uid.hashCode());

        System.out.println("[+] Starting the server");
        /*Server server = ServerBuilder.forPort(8080).addService(new HelloServiceImpl()).build();

        try {
            server.start();
            server.awaitTermination();
        } catch (Exception e) {
            System.out.println("[-] Exception was made");
            System.out.println(e.toString());
        }*/


    }
}
