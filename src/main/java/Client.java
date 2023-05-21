import AuctionMechanism.TransactionTypes.BidAuctionTransaction;
import AuctionMechanism.TransactionTypes.CreateAuctionTransaction;
import AuctionMechanism.TransactionTypes.Transaction;
import AuctionMechanism.Wallet.Wallet;
import AuctionMechanism.util.Item;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import kademlia.KademliaNode;

import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        BigInteger uid = new BigInteger("1234567890123456789012345678901234567892",16);

        System.out.println(uid.hashCode());


        KademliaNode kademliaNode = new KademliaNode("localhost",uid,8081,null);

       // kademliaNode.pingNode("localhost",8080);



        Scanner in= new Scanner(System.in);
        Wallet wallet=null;

        int keyChoice = -1;
        System.out.println("1 - Create new Key Pair\n2 - Import Key Pair\n");
        System.out.println("Please Choose an option between 1 and 2");
        keyChoice = in.nextInt();
        while(keyChoice > 2 || keyChoice < 1){
            System.out.println("Please Choose an option between 1 and 5");
            keyChoice = in.nextInt();
        }

        System.out.println("Please Enter the Path of the Key Pair");
        in.nextLine();
        String path = in.nextLine();

        System.out.println("Please Enter the Password of the Key Pair");

        String pass = in.nextLine();

        switch (keyChoice){
            case 1 :
                wallet = new Wallet();
                try {
                    wallet.saveToFile(path, pass);
                    System.out.println("Alice's wallet saved to " + path);
                } catch (Exception e) {
                    System.err.println("Error saving wallet: " + e.getMessage());
                }
                break;
            case 2 :
                try {
                    wallet = Wallet.loadFromFile(path, pass);
                    System.out.println("Loaded wallet from " + path);
                } catch (Exception e) {
                    System.err.println("Error loading wallet: " + e.getMessage());
                }
                break;
            default : break;
        }



        // IMPORT OR CREATE PAIR OF PUBLIC AND PRIVATE KEY FOR AUTHENTICATION
        int choice = -1;
        List<Transaction> transactions = null;
        while (choice != 5){


            System.out.println("1 - Create Auction\n2 - See Open Auctions\n3 - Make a Bid\n4 - See Winning Bids\n5 - Exit\n");
            System.out.println("Please Choose an option between 1 and 5");
            choice = in.nextInt();
            while (choice < 1 || choice > 5){
                System.out.println("Please Choose an option between 1 and 5");
                choice = in.nextInt();
            }

            switch (choice){
                case 1 :
                    // Call Create Auction Function
                    System.out.println("Provide item name:" );
                    String itemName = in.nextLine();
                    System.out.println("Provide item description:" );
                    String itemDesc = in.nextLine();
                    System.out.println("Provide minimum bid:" );
                    int minimumBid = in.nextInt();
                    Item item = new Item(itemName, itemDesc);
                    CreateAuctionTransaction tx = new CreateAuctionTransaction(wallet.getPublicKey(), item, minimumBid);
                    //Call Node Brodcast Function

                    System.out.println("Created Auction for Item:" + itemName + " with description:" + itemDesc + " for user:" + wallet.getPublicKey().toString());
                    break;
                case 2 :
                    transactions = kademliaNode.getOpenAuctions();
                    for (Transaction i : transactions){
                        System.out.println("Item ID:" + i.getAuctionedItem().getId() + "Item Name:" + i.getAuctionedItem().getName() + " Item Description:" + i.getAuctionedItem().getDescription());
                    }

                    break;
                case 3 :
                    transactions = kademliaNode.getOpenAuctions();
                    for (Transaction i : transactions){
                        System.out.println("Item ID:" + i.getAuctionedItem().getId() + "Item Name:" + i.getAuctionedItem().getName() + " Item Description:" + i.getAuctionedItem().getDescription());
                    }
                    System.out.println("Please Choose an option between 1 and " + transactions.size());
                    int itemChoice = in.nextInt();
                    while (itemChoice < 1 || itemChoice > transactions.size()){
                        System.out.println("Please Choose an option between 1 and " + transactions.size());
                        itemChoice = in.nextInt();
                    }
                    System.out.println("Please Enter the Bid Amount");
                    int bidAmount = in.nextInt();
                    BidAuctionTransaction bidTx = new BidAuctionTransaction(transactions.get(itemChoice).getSellerPublicKey(), wallet.getPublicKey(), transactions.get(itemChoice-1).getAuctionedItem(), bidAmount);
                    byte[] signature = wallet.signTransaction(bidTx.getHash());
                    bidTx.setSignature(signature);
                    kademliaNode.commitTransaction(bidTx);
                    break;
                case 4 :
                    kademliaNode.getWinningAuctions();
                    break;
                case 5 :
                    System.out.println("Exiting...");
                    break;
                default :
                    System.out.println("Choice default");
                    break;

            }
        }





    }



}
