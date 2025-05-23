package kademlia.grpc;

import AuctionMechanism.Block;
import AuctionMechanism.Blockchain;
import AuctionMechanism.TransactionTypes.Transaction;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import kademlia.Bucket;
import kademlia.KademliaNode;
import kademlia.RoutingTable;
import kademlia.Util;
import kademlia.grpc.builders.*;

import java.io.*;
import java.math.BigInteger;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Class that is responsible for implementing all GRPC functions.
 */
public class KademliaGrpc extends KademliaServiceGrpc.KademliaServiceImplBase implements Serializable{

    private final KademliaNode kademliaNode; // source node

    public KademliaGrpc(KademliaNode kademliaNode) {
        this.kademliaNode = kademliaNode;
    }



    /**
     * This function handles the PING requests.
     * @param request with the Node that pinged
     * @param responseObserver callback
     */
    @Override
    public void ping(PingRequest request, StreamObserver<PingResponse> responseObserver) {
        Node sourceNode = request.getSender();

        // received a ping
        System.out.println("[+] ["+this.kademliaNode.getPort()+"] [RECEIVED PING FROM " + sourceNode.getAddress() + "]");

        PingResponse response = PingResponse.newBuilder()
                .setSender(Node.newBuilder()
                        .setAddress(this.kademliaNode.getAddress())
                        .setId(ByteString.copyFromUtf8(this.kademliaNode.getId().toString()))
                        .setAddressBytes(ByteString.copyFromUtf8("asd"))).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

        //this.kademliaNode.getRoutingTable().

        // adds the node to the routing table
        this.addNodeToRoutingTable(sourceNode);
        // send the routing table
        this.sendRoutingTable(sourceNode);
        // send the blockchain
        this.sendBlockchain(sourceNode);
    }


    /**
     * Instructs a node to store a key-value pair
     * This function handles the STORE requests.
     * @param request StoreRequest
     * @param responseObserver StreamObserver
     */
    @Override
    public void store(StoreRequest request, StreamObserver<StoreResponse> responseObserver) {
        super.store(request, responseObserver);
        Node sourceNode = request.getSender();
        // deserialize the block

        String key = new String(request.getKey().toByteArray());
        if(key.equals("BLOCKCHAIN")){
            System.out.println("[+] ["+this.kademliaNode.getPort()+"] [RECEIVED STORE BLOCKCHAIN FROM " + sourceNode.getAddress() + "]");
            byte[] blockchainBytes = request.getValue().toByteArray();
            Blockchain blockchain = null;
            try {
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(blockchainBytes));
                blockchain = (Blockchain) ois.readObject();
            } catch (Exception e) {
                System.out.println("[-] Exception reading bytes from Block chain");
            }
            System.out.println("[+] ["+this.kademliaNode.getPort()+"] [REPLACING BLOCKCHAIN WITH THE ONE RECEIVED]");
            this.kademliaNode.setBlockchain(blockchain); // TODO usar funcao para validar se a blockchain e valida ou nao
        }

        if (key.equals("ROUTING_TABLE")) {
            System.out.println("[+] ["+this.kademliaNode.getPort()+"] [RECEIVED STORE ROUTING TABLE FROM " + sourceNode.getAddress() + "]");
            byte[] routingTableBytes = request.getValue().toByteArray();
            List<Bucket> buckets = null;
            try {
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(routingTableBytes));
                buckets = (List<Bucket>) ois.readObject();
            } catch (Exception e) {
                System.out.println("[-] Exception reading bytes from Routing Table " + e.toString());
            }
            System.out.println("[+] ["+this.kademliaNode.getPort()+"] [REPLACING ROUTING TABLE WITH THE ONE RECEIVED]");
            this.kademliaNode.setRoutingTable(buckets); // replace routing table with the one received
            this.kademliaNode.getRoutingTable().update(this.getKademliaNodeFromNode(sourceNode)); // adds the contacted node to the routing table
            boolean removed = this.kademliaNode.getRoutingTable().remove(this.kademliaNode); // removes itself from routing table
        }


        if(key.equals("TRANSACTION")) {
            System.out.println("[+] ["+this.kademliaNode.getPort()+"] [RECEIVED STORE TRANSACTION FROM " + sourceNode.getAddress() + "]");
            byte[] transactionBytes = request.getValue().toByteArray();
            Transaction transaction = null;
            try {
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(transactionBytes));
                transaction = (Transaction) ois.readObject();
            } catch (Exception e) {
                System.out.println("[-] Exception reading bytes from Transaction " + e.toString());
            }
            if (transaction != null){
                System.out.println("[+] ["+this.kademliaNode.getPort()+"] [RECEIVED TRANSACTION!]");
                if (this.kademliaNode.isTransactionValid(transaction) && !this.kademliaNode.getMempoolTransactions().contains(transaction)){
                    // if transaction is valid and not present in the mempool
                    this.kademliaNode.getMempoolTransactions().add(transaction);
                }
                return;
            }
            System.out.println("[-] Transaction is null , returning");
        }

        if (key.equals("BLOCK")) {
            System.out.println("[+] ["+this.kademliaNode.getPort()+"] [RECEIVED STORE BLOCK FROM " + sourceNode.getAddress() + "]");
            byte[] blockBytes = request.getValue().toByteArray();
            Block block = null;
            try {
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(blockBytes));
                block = (Block) ois.readObject();
            } catch (Exception e) {
                System.out.println("[-] Exception reading bytes from Block " + e.toString());
            }
            System.out.println("[+] ["+this.kademliaNode.getPort()+"] [ADDING BLOCK TO BLOCKCHAIN] COM ESTAS TRANSACTIONS:" + this.kademliaNode.getMempoolTransactions().size());
            this.kademliaNode.getBlockchain().addBlock(block);
            System.out.println("[+] ["+this.kademliaNode.getPort()+"] [REMOVE MEMPOOL TRANSACTIONS DEPOIS DE ADICIONAR BLOCK], ANTES TINHA:" + this.kademliaNode.getMempoolTransactions().size());
            this.kademliaNode.removeMempoolTransactions(block.getTransactions());
            //this.kademliaNode.getMempoolTransactions().remove(0);
            /*int j = 0;
            block.printBlock();
            for (Transaction tr : block.getTransactions()) {
                {
                    System.out.println("Transaction: ONDE DEVIA APAGAR:TRX:" + (j+1) + " com hash:" + tr.getHash());
                    System.out.println("Transaction: ONDE DEVIA APAGAR:TRX:" + (j+1) + " com signature:" + tr.getSignature());
                    System.out.println("Transaction: ONDE DEVIA APAGAR:TRX:" + (j+1) + " com item:" + tr.getAuctionedItem());
                    System.out.println("Block: ONDE DEVIA APAGAR:TRX:" + j + " com hash:" + block.getTransactions().get(j).getHash());
                    System.out.println("Block:ONDE DEVIA APAGAR:TRX:" + j + " com signature:" + block.getTransactions().get(j).getSignature());
                    System.out.println("Block:ONDE DEVIA APAGAR:TRX:" + j + " com item:" + block.getTransactions().get(j).getAuctionedItem());
                    j++;
                }
                this.kademliaNode.getMempoolTransactions().remove(tr);
            }*/

            System.out.println("[+] [" + this.kademliaNode.getPort() + "] Block Transactions size: " + block.getTransactions().size());
            System.out.println("[+] [" + this.kademliaNode.getPort() + "] mempoolTransactions size: " + this.kademliaNode.getMempoolTransactions().size());


        }




        /*byte[] blockBytes = request.getValue().toByteArray();
        Block newBlock = null;
        try {
               ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(blockBytes));
                newBlock = (Block) ois.readObject();
        } catch (Exception e) {
            System.out.println("[-] Exception reading bytes from Block chain");
        }
        //adds the block to the current blockchain
        this.kademliaNode.getBlockchain().addBlock(newBlock);*/

    }

    /**
     * Returns information about the k nodes closest to the target id
     * @param request FindNodeRequest
     * @param responseObserver StreamObserver
     */
    @Override
    public void findNode(FindNodeRequest request, StreamObserver<FindNodeResponse> responseObserver) {
        super.findNode(request, responseObserver);
        Node sourceNode = request.getSender();

        System.out.println("[+] [NODE FIND NODE MESSAGE] | IP: " + sourceNode.getAddress() + " | ID: " + sourceNode.getId());

        BigInteger targetToFindNodeUid = new BigInteger(request.getTarget().toByteArray());

        //find closest nodes
        List<KademliaNode> closestNodes = this.kademliaNode.getRoutingTable().findClosestNodes(targetToFindNodeUid,3);

        List<Node> nodes = new ArrayList<>();
        // TODO: passar KademliaNode para Node

        FindNodeResponse response = FindNodeResponse.newBuilder()
                .setSender(Node.newBuilder()
                        .setAddress(this.kademliaNode.getAddress())
                        .setId(ByteString.copyFromUtf8(this.kademliaNode.getId().toString()))
                        .setAddressBytes(ByteString.copyFromUtf8("asd")))
                .addAllNodes(nodes)
                .build();



        // descobrir os nos mais pertos de um determinado no pela distancia das routing tables

    }


    /**
     * Similar to the FIND_NODE RPC, but if the recipient has received a STORE
     * for the given key, it just returns the stored value.
     * @param request FindValueRequest
     * @param responseObserver StreamObserver
     */
    @Override
    public void findValue(FindValueRequest request, StreamObserver<FindValueResponse> responseObserver) {
        super.findValue(request, responseObserver);
        // a mesma coiusa que o findNode , mas é para encrontrar valores de key-value
        // usado para pedir a blockchain toda ou outros valores

        Node sourceNode = request.getSender();
        System.out.println("[+] ["+this.kademliaNode.getPort()+"] [RECEIVED FIND VALUE FROM "+sourceNode.getAddress()+"] | KEY: " + request.getKey());

        String key = new String(request.getKey().toByteArray());

        // if the node wants to get full blockchain - new joiner
        if (key.equals("BLOCKCHAIN")){
            Blockchain blockchain = this.kademliaNode.getBlockchain();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                ObjectOutput out = new ObjectOutputStream(bos);
                out.writeObject(blockchain);
                out.flush();
            } catch (Exception e) {
                System.out.println("[-] Exception on serializing the Blockchain");
            }

            byte[] blockchainBytes = bos.toByteArray();
            FindValueResponse findValueResponse = FindValueResponse.newBuilder()
                    .setValue(ByteString.copyFrom(blockchainBytes))
                    .setSender(Node.newBuilder()
                            .setAddress(this.kademliaNode.getAddress())
                            .setId(ByteString.copyFromUtf8(this.kademliaNode.getId().toString()))
                            .setAddressBytes(ByteString.copyFromUtf8(this.kademliaNode.getAddress())))
                    .build();

            System.out.println("sending blockchain");
            responseObserver.onNext(findValueResponse);
            responseObserver.onCompleted();
        }

    }

    private KademliaNode getKademliaNodeFromNode(Node sourceNode) {
        String address = sourceNode.getAddress();
        String ip = address.split(":")[0];
        int port = Integer.parseInt(address.split(":")[1]);
        BigInteger id =  new BigInteger(sourceNode.getId().toByteArray());
        PublicKey publicKey = Util.stringToPublicKey(sourceNode.getPubKey());
        // update RoutingTable if node that pinged
        return new KademliaNode(ip,id,port,publicKey);
    }

    /**
     * Adds a source node to the routing table if it was not added previously
     * @param sourceNode Source node to add to the routing table
     */
    private void addNodeToRoutingTable(Node sourceNode){
        KademliaNode newKademliaNode = this.getKademliaNodeFromNode(sourceNode);
        //System.out.println("Updating routing table with id " + id.toString());
        this.kademliaNode.getRoutingTable().update(newKademliaNode);
    }

    private void sendBlockchain(Node sourceNode){
        KademliaNode kademliaNode1 = this.getKademliaNodeFromNode(sourceNode);
        System.out.println("[+] ["+this.kademliaNode.getPort()+"] SENDING COPY OF BLOCKCHAIN TO " + kademliaNode1.getPort());
        this.kademliaNode.store(kademliaNode1,"BLOCKCHAIN",null,null);
    }

    private void sendRoutingTable(Node sourceNode){
        KademliaNode newKademliaNode = this.getKademliaNodeFromNode(sourceNode);
        this.kademliaNode.store(newKademliaNode,"ROUTING_TABLE",null,null);
    }
}
