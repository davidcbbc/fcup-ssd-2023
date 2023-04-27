package kademlia;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RoutingTable {

    private final KademliaNode self;

    private List<Bucket> buckets;


    public RoutingTable(KademliaNode self){
        this.self = self;
        this.buckets = new ArrayList<>();
        // create buckets
        for (int i = 0; i < 160; i++) {
            this.buckets.add(new Bucket());
        }

    }

    /**
     * Updates the bucket with new node if it doesn't exist
     * @param node new joiner
     */
    public synchronized void update(KademliaNode node){
        if (node.equals(this.self)){
            return;
        }

        int index = getBucketIndex(node);

        Bucket bucket = buckets.get(index);
        bucket.update(node);
    }

    /**
     * Gets the correct bucket index by xoring the ids of the nodes
     * @param node to search
     * @return bucket index
     */
    private int getBucketIndex(KademliaNode node) {
        BigInteger distance = self.getId().xor(node.getId());
        int index = distance.bitLength() - 1;

        return index < 0 ? 0 : index;
    }

    /**
     * Gets the correct bucket index by xoring BigInteger ids
     * @param targetId BigInteger id of the node
     * @return bucket index
     */
    private int getBucketIndex(BigInteger targetId) {
        BigInteger distance = self.getId().xor(targetId);
        int index = distance.bitLength() - 1;

        return index < 0 ? 0 : index;
    }

    /**
     * Returns the closest nodes by searching to the buckets - find
     * the k closest nodes to a given target ID
     * @param targetId The target ID for which we want to find the closest nodes.
     * @param numResults The number of closest nodes we want to find (k)
     * @return closest nodes
     */
    public List<KademliaNode> findClosestNodes(BigInteger targetId, int numResults) {
        List<KademliaNode> closestNodes = new ArrayList<>();

        int index = getBucketIndex(targetId);
        int left = index - 1;
        int right = index + 1;

        closestNodes.addAll(buckets.get(index).getNodes());

        while (closestNodes.size() < numResults && (left >= 0 || right < buckets.size())) {
            if (left >= 0) {
                closestNodes.addAll(buckets.get(left).getNodes());
                left--;
            }

            if (right < buckets.size()) {
                closestNodes.addAll(buckets.get(right).getNodes());
                right++;
            }
        }

        closestNodes.sort((a, b) -> a.getId().xor(targetId).compareTo(b.getId().xor(targetId)));

        return closestNodes.subList(0, Math.min(numResults, closestNodes.size()));
    }


}
