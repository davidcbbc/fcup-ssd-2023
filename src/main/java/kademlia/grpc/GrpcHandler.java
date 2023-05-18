package kademlia.grpc;

import io.grpc.ServerBuilder;

/**
 * This class starts the listeners for all the GRPC methods implemented in KademliaGrpc class
 */
public class GrpcHandler extends Thread{

    private final int port;
    private final KademliaGrpc kademliaGrpc;

    public GrpcHandler(int port, KademliaGrpc kademliaGrpc) {
        this.port = port;
        this.kademliaGrpc = kademliaGrpc;
    }

    /**
     * Starts a new thread with the server
     */
    @Override
    public void run() {
        // Start listeners
        io.grpc.Server server = ServerBuilder
                .forPort(this.port)
                .addService(this.kademliaGrpc).build();

        try {
            System.out.println("[+] Node handler started on port " + this.port);
            server.start();
            server.awaitTermination();
        } catch (Exception e) {
            System.out.println("[-] Exception was made while starting the node handler");
            System.out.println(e.toString());
        }
    }
}
