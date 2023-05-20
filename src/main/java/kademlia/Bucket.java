package kademlia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Bucket implements Serializable {

    private static final int MAX_SIZE = 20;
    private LinkedList<KademliaNode> nodes;

    public Bucket(){
        this.nodes = new LinkedList<>();
    }

    public synchronized void update(KademliaNode node){
        if(this.nodes.contains(node)) {
            // move to the end of the list
            this.nodes.remove(node);
            this.nodes.addLast(node);
            return;
        }

        if (this.nodes.size() < this.MAX_SIZE){
            // adds new node to the end of the list
            nodes.addLast(node);
            return;
        }

        // bucket is full , splitting or refreshing nodes
        System.out.println("[+] Bucket is full , ignoring node");
    }

    public synchronized List<KademliaNode> getNodes(){
        return new ArrayList<>(this.nodes);
    }

    public boolean removeNode(KademliaNode target) {
        return nodes.removeIf(node -> node.getId().equals(target.getId()));
    }


}
