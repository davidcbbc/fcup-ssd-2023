package kademlia;

import java.net.InetAddress;
import java.security.PublicKey;

public class Node {
    private InetAddress address;
    private int port;
    private byte[] id;
    private PublicKey publicKey;

    public Node(InetAddress address, int port) {
        this.address = address;
        this.port = port;
        // TODO Implementar o nounce para uid
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public byte[] getId() {
        return id;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public String printNode(){
        return this.getAddress().getHostAddress() + ":" + this.getPort();
    }
}
